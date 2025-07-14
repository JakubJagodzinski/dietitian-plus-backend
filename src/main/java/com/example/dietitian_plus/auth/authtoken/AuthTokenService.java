package com.example.dietitian_plus.auth.authtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    public boolean isAuthTokenValidInDatabase(String authToken) {
        AuthToken databaseAuthToken = authTokenRepository.findByAuthToken(authToken).orElse(null);

        if (databaseAuthToken == null) {
            return false;
        }

        return databaseAuthToken.getAuthTokenType() == AuthTokenType.ACCESS && !databaseAuthToken.getIsExpired() && !databaseAuthToken.getIsRevoked();
    }

}
