package com.example.dietitian_plus.auth;

import com.example.dietitian_plus.auth.dto.request.AuthenticationRequestDto;
import com.example.dietitian_plus.auth.dto.request.RefreshTokenRequestDto;
import com.example.dietitian_plus.auth.dto.request.RegisterRequestDto;
import com.example.dietitian_plus.auth.dto.response.AuthenticationResponseDto;
import com.example.dietitian_plus.auth.dto.response.RefreshTokenResponseDto;
import com.example.dietitian_plus.auth.jwt.JwtService;
import com.example.dietitian_plus.auth.authtoken.AuthToken;
import com.example.dietitian_plus.auth.authtoken.AuthTokenRepository;
import com.example.dietitian_plus.auth.authtoken.AuthTokenType;
import com.example.dietitian_plus.common.constants.messages.EmailMessages;
import com.example.dietitian_plus.common.constants.messages.TokenMessages;
import com.example.dietitian_plus.common.constants.messages.UserMessages;
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
    private final AuthTokenRepository authTokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private final VerificationTokenService verificationTokenService;

    private AuthenticationResponseDto generateUserToken(User user) {
        String jwtToken = jwtService.generateToken(user);
        saveUserAuthToken(user, jwtToken, AuthTokenType.ACCESS);

        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserAuthToken(user, refreshToken, AuthTokenType.REFRESH);

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
            throw new MailSendException(EmailMessages.FAILED_TO_SEND_VERIFICATION_EMAIL);
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

        revokeAllUserAuthTokens(user);

        return generateUserToken(user);
    }

    private void saveUserAuthToken(User user, String tokenValue, AuthTokenType authTokenType) {
        AuthToken authToken = new AuthToken();

        authToken.setAuthToken(tokenValue);
        authToken.setAuthTokenType(authTokenType);
        authToken.setUser(user);

        authTokenRepository.save(authToken);
    }

    private void revokeAllUserAuthTokens(User user) {
        List<AuthToken> validUserAuthTokens = authTokenRepository.findAllByUser_UserIdAndIsExpiredFalseOrIsRevokedFalse(user.getUserId());

        if (validUserAuthTokens.isEmpty()) {
            return;
        }

        validUserAuthTokens.forEach(
                authToken -> {
                    authToken.setIsExpired(true);
                    authToken.setIsRevoked(true);
                }
        );

        authTokenRepository.saveAll(validUserAuthTokens);
    }

    @Transactional
    public RefreshTokenResponseDto refreshAuthToken(RefreshTokenRequestDto requestDto) throws IllegalArgumentException {
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

        AuthToken authToken = authTokenRepository.findByAuthToken(refreshToken).orElse(null);

        if (authToken == null) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        if (authToken.getAuthTokenType() != AuthTokenType.REFRESH) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        if (!jwtService.isTokenValid(refreshToken, user) || authToken.getIsExpired() || authToken.getIsRevoked()) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        revokeAllUserAuthTokens(user);

        String newAccessToken = jwtService.generateToken(user);
        saveUserAuthToken(user, newAccessToken, AuthTokenType.ACCESS);

        String newRefreshToken = jwtService.generateRefreshToken(user);
        saveUserAuthToken(user, newRefreshToken, AuthTokenType.REFRESH);

        return RefreshTokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
