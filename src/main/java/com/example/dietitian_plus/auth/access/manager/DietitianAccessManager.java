package com.example.dietitian_plus.auth.access.manager;

import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DietitianAccessManager {

    private final SecurityUtils securityUtils;

    public void checkCanAccessDietitian(UUID dietitianId) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dietitianId.equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(DietitianMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DIETITIAN);
        }
    }

    public void checkCanUpdateDietitian(UUID dietitianId) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dietitianId.equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(DietitianMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DIETITIAN);
        }
    }

    public void checkCanDeleteDietitian(UUID dietitianId) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dietitianId.equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(DietitianMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DIETITIAN);
        }
    }

}
