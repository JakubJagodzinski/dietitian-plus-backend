package com.example.dietitian_plus.auth;

import com.example.dietitian_plus.auth.authtoken.AuthToken;
import com.example.dietitian_plus.auth.authtoken.AuthTokenRepository;
import com.example.dietitian_plus.common.constants.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final AuthTokenRepository authTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(SecurityConstants.HEADER_START)) {
            return;
        }

        final String jwt = authHeader.substring(SecurityConstants.HEADER_START.length());

        AuthToken storedAuthToken = authTokenRepository.findByAuthToken(jwt).orElse(null);

        if (storedAuthToken != null) {
            storedAuthToken.setIsExpired(true);
            storedAuthToken.setIsRevoked(true);

            authTokenRepository.save(storedAuthToken);

            SecurityContextHolder.clearContext();
        }
    }

}
