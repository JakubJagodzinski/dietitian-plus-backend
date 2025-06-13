package com.example.dietitian_plus.auth.access;

import com.example.dietitian_plus.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public User getCurrentUser() {
        return (User) getAuthentication().getPrincipal();
    }

    public boolean isOwner(Long expectedUserId) {
        return getCurrentUserId().equals(expectedUserId);
    }

    public boolean hasRole(String role) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    public boolean hasPermission(String authority) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }

}
