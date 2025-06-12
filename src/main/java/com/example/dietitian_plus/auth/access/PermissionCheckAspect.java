package com.example.dietitian_plus.auth.access;

import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionCheckAspect {

    private final SecurityUtils securityUtils;

    @Before("@annotation(checkPermission)")
    public void checkPermission(CheckPermission checkPermission) {
        Permission permission = checkPermission.value();
        Authentication auth = securityUtils.getAuthentication();

        boolean hasPermission = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(permission.getPermission()));

        if (!hasPermission) {
            throw new AccessDeniedException("You do not have permission: " + permission);
        }
    }

}
