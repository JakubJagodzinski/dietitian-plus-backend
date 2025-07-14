package com.example.dietitian_plus.paywall;

import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.AccountSubscriptionMessages;
import com.example.dietitian_plus.common.dto.MessageResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaywallFilter extends OncePerRequestFilter {

    private static final List<String> SUBSCRIPTION_FREE_API_ENDPOINTS = List.of(
            //public
            "/api/v1/payments/**",
            "/api/v1/subscriptions/webhook",
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",

            "/api/v1/subscriptions/**",
            "/api/v1/users/**",
            "/api/v1/patients",
            "/api/v1/patients/*",
            "/api/v1/patients/*/dietitians",
            "/api/v1/patients/*/questionnaire",
            "/api/v1/patients/*/questionnaire-status",
            "/api/v1/dietitians/*",
            "/api/v1/dietitians/*/patients"
    );

    private final PaywallService paywallService;

    private final SecurityUtils securityUtils;

    private final ObjectMapper objectMapper;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private boolean isPathSubscriptionFree(String path) {
        return SUBSCRIPTION_FREE_API_ENDPOINTS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isFree = isPathSubscriptionFree(path);
        boolean hasSub = paywallService.hasActiveSubscription();

        if (isFree || isAdmin || hasSub) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(
                    new MessageResponseDto(AccountSubscriptionMessages.ACCOUNT_SUBSCRIPTION_REQUIRED)
            ));
        }
    }

}
