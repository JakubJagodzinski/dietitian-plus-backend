package com.example.dietitian_plus.auth.access.evaluator;

import com.example.dietitian_plus.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public Long getCurrentUserId(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        return userDetails.getId();
    }

    public boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role));
    }

    public boolean hasAuthority(Authentication authentication, String authority) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
    }

    public boolean isOwner(Long resourceId, Authentication authentication) {
        return getCurrentUserId(authentication).equals(resourceId);
    }

}

