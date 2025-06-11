package com.example.dietitian_plus.auth.access.evaluator;

import com.example.dietitian_plus.user.Permission;
import com.example.dietitian_plus.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("permissionEvaluator")
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final SecurityUtils securityUtils;

    public boolean isOwnerDietitian(Long dietitianId, Authentication authentication) {
        return isAdmin(authentication) ||
                (securityUtils.hasAuthority(authentication, Permission.DIETITIAN_READ.getPermission()) &&
                        securityUtils.isOwner(dietitianId, authentication));
    }

    public boolean isOwnerPatient(Long patientId, Authentication authentication) {
        return isAdmin(authentication) ||
                (securityUtils.hasAuthority(authentication, Permission.PATIENT_READ.getPermission()) &&
                        securityUtils.isOwner(patientId, authentication));
    }

    public boolean isOwnerOrAdmin(Long userId, Authentication authentication) {
        return isAdmin(authentication) || securityUtils.isOwner(userId, authentication);
    }

    public boolean isOwner(Long userId, Authentication authentication) {
        return securityUtils.isOwner(userId, authentication);
    }

    public boolean isAdmin(Authentication authentication) {
        return securityUtils.hasRole(authentication, Role.ADMIN.name());
    }

    public boolean isDietitian(Authentication authentication) {
        return securityUtils.hasRole(authentication, Role.DIETITIAN.name());
    }

    public boolean isPatient(Authentication authentication) {
        return securityUtils.hasRole(authentication, Role.PATIENT.name());
    }

}
