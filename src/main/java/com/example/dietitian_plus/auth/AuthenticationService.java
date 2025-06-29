package com.example.dietitian_plus.auth;

import com.example.dietitian_plus.auth.dto.request.AuthenticationRequestDto;
import com.example.dietitian_plus.auth.dto.request.RefreshTokenRequestDto;
import com.example.dietitian_plus.auth.dto.request.RegisterRequestDto;
import com.example.dietitian_plus.auth.dto.response.AuthenticationResponseDto;
import com.example.dietitian_plus.auth.dto.response.RefreshTokenResponseDto;
import com.example.dietitian_plus.auth.jwt.JwtService;
import com.example.dietitian_plus.auth.token.Token;
import com.example.dietitian_plus.auth.token.TokenRepository;
import com.example.dietitian_plus.auth.token.TokenType;
import com.example.dietitian_plus.common.constants.messages.TokenMessages;
import com.example.dietitian_plus.common.constants.messages.UserMessages;
import com.example.dietitian_plus.common.constants.messages.VerificationEmailMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.email.EmailService;
import com.example.dietitian_plus.user.Role;
import com.example.dietitian_plus.user.User;
import com.example.dietitian_plus.user.UserRepository;
import com.example.dietitian_plus.verification.VerificationTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private final VerificationTokenService verificationTokenService;

    private AuthenticationResponseDto generateUserToken(User user) {
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken, TokenType.ACCESS);

        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    @Transactional
    public void register(RegisterRequestDto registerRequestDto) throws IllegalArgumentException, MailException {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new IllegalArgumentException(UserMessages.EMAIL_IS_ALREADY_TAKEN);
        }

        Role userRole = parseRole(registerRequestDto.getRole());
        User user = createUserInstance(userRole);

        populateCommonUserFields(user, registerRequestDto);

        User savedUser = userRepository.save(user);

        String verificationToken = verificationTokenService.createVerificationToken(savedUser);

        try {
            emailService.sendRegistrationEmail(savedUser.getEmail(), savedUser.getFirstName(), savedUser.getRole(), verificationToken);
        } catch (Exception e) {
            throw new MailSendException(VerificationEmailMessages.FAILED_TO_SEND_VERIFICATION_EMAIL);
        }
    }

    private Role parseRole(String role) throws IllegalArgumentException {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(UserMessages.INVALID_USER_ROLE);
        }
    }

    private User createUserInstance(Role role) throws IllegalArgumentException {
        return switch (role) {
            case DIETITIAN -> new Dietitian();
            case PATIENT -> new Patient();
            default -> throw new IllegalArgumentException(UserMessages.INVALID_USER_ROLE);
        };
    }

    private void populateCommonUserFields(User user, RegisterRequestDto dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        user.setPhoneNumber(dto.getPhoneNumber());
    }

    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) throws AccessDeniedException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new AccessDeniedException(UserMessages.WRONG_USERNAME_OR_PASSWORD);
        }

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            throw new AccessDeniedException(UserMessages.WRONG_USERNAME_OR_PASSWORD);
        }

        if (!user.isVerified()) {
            throw new AccessDeniedException(UserMessages.ACCOUNT_NOT_VERIFIED);
        }

        user.setLastLoggedIn(LocalDateTime.now());
        userRepository.save(user);

        revokeAllUserTokens(user);

        return generateUserToken(user);
    }

    private void saveUserToken(User user, String tokenValue, TokenType tokenType) {
        Token token = new Token();

        token.setToken(tokenValue);
        token.setTokenType(tokenType);
        token.setUser(user);

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllByUser_UserIdAndIsExpiredFalseOrIsRevokedFalse(user.getUserId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(
                token -> {
                    token.setIsExpired(true);
                    token.setIsRevoked(true);
                }
        );

        tokenRepository.saveAll(validUserTokens);
    }

    @Transactional
    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto requestDto) throws IllegalArgumentException {
        String refreshToken = requestDto.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        User user = userRepository.findByEmail(userEmail).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        Token token = tokenRepository.findByToken(refreshToken).orElse(null);

        if (token == null) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        if (token.getTokenType() != TokenType.REFRESH) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        if (!jwtService.isTokenValid(refreshToken, user) || token.getIsExpired() || token.getIsRevoked()) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        revokeAllUserTokens(user);

        String newAccessToken = jwtService.generateToken(user);
        saveUserToken(user, newAccessToken, TokenType.ACCESS);

        String newRefreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, newRefreshToken, TokenType.REFRESH);

        return RefreshTokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
