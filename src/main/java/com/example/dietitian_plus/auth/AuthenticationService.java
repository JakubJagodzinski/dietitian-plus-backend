package com.example.dietitian_plus.auth;

import com.example.dietitian_plus.auth.dto.*;
import com.example.dietitian_plus.auth.jwt.JwtService;
import com.example.dietitian_plus.auth.jwt.Token;
import com.example.dietitian_plus.auth.jwt.TokenRepository;
import com.example.dietitian_plus.auth.jwt.TokenType;
import com.example.dietitian_plus.common.constants.messages.TokenMessages;
import com.example.dietitian_plus.common.constants.messages.UserMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.user.Role;
import com.example.dietitian_plus.user.User;
import com.example.dietitian_plus.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

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

    public void register(RegisterRequestDto registerRequestDto) throws IllegalArgumentException {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new IllegalArgumentException(UserMessages.USER_ALREADY_EXISTS);
        }

        Role userRole = parseRole(registerRequestDto.getRole());
        User user = createUserInstance(userRole);

        populateCommonUserFields(user, registerRequestDto);

        userRepository.save(user);
    }

    private Role parseRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(UserMessages.INVALID_USER_ROLE);
        }
    }

    private User createUserInstance(Role role) {
        return switch (role) {
            case PATIENT -> new Patient();
            case DIETITIAN -> new Dietitian();
            default -> throw new IllegalArgumentException(UserMessages.INVALID_USER_ROLE);
        };
    }

    private void populateCommonUserFields(User user, RegisterRequestDto dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) throws EntityNotFoundException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        authenticationManager.authenticate(authenticationToken);

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException(UserMessages.USER_NOT_FOUND);
        }

        revokeAllUserTokens(user);

        return generateUserToken(user);
    }

    public void saveUserToken(User user, String tokenValue, TokenType tokenType) {
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

    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto requestDto) throws IllegalArgumentException, UsernameNotFoundException {
        String refreshToken = requestDto.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException(TokenMessages.REFRESH_TOKEN_IS_MISSING);
        }

        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new IllegalArgumentException(TokenMessages.TOKEN_SUBJECT_IS_INVALID_OR_MISSING);
        }

        User user = userRepository.findByEmail(userEmail).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException(UserMessages.USER_NOT_FOUND);
        }

        Token token = tokenRepository.findByToken(refreshToken).orElse(null);

        if (token == null) {
            throw new IllegalArgumentException(TokenMessages.TOKEN_NOT_FOUND);
        }

        if (token.getTokenType() != TokenType.REFRESH) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_TOKEN_IS_NOT_A_REFRESH_TOKEN);
        }

        if (!jwtService.isTokenValid(refreshToken, user) || token.getIsExpired() || token.getIsRevoked()) {
            throw new IllegalArgumentException(TokenMessages.INVALID_OR_EXPIRED_REFRESH_TOKEN);
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
