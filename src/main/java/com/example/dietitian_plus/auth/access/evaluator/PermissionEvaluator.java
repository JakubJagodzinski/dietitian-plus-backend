package com.example.dietitian_plus.auth.access.evaluator;

import com.example.dietitian_plus.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("permissionEvaluator")
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final SecurityUtils securityUtils;

    public boolean isAdmin(Authentication authentication) {
        return securityUtils.hasRole(authentication, Role.ADMIN.name());
    }

    public boolean isDietitian(Authentication authentication) {
        return securityUtils.hasRole(authentication, Role.DIETITIAN.name());
    }

    public boolean isOwnerDietitian(Long dietitianId, Authentication authentication) {
        return securityUtils.hasRole(authentication, Role.DIETITIAN.name()) && securityUtils.isOwner(dietitianId, authentication);
    }

    public boolean isPatient(Authentication authentication) {
        return securityUtils.hasRole(authentication, Role.PATIENT.name());
    }

    public boolean isOwnerPatient(Long patientId, Authentication authentication) {
        return securityUtils.hasRole(authentication, Role.PATIENT.name()) && securityUtils.isOwner(patientId, authentication);
    }

}
