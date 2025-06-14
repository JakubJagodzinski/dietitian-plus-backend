package com.example.dietitian_plus.auth.access.manager;

import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.patient.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientDislikedProductAccessManager {

    private final SecurityUtils securityUtils;

    public void checkCanAccessPatientDislikedProducts(Patient patient) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isPatientOwner = patient.getUserId().equals(currentUserId);
        boolean isPatientDietitianOwner = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isPatientOwner && !isPatientDietitianOwner) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanAssignDislikedProductToPatient(UUID patientId) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isPatientOwner = patientId.equals(currentUserId);

        if (!isAdmin && !isPatientOwner) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanUnassignDislikedProductFromPatient(UUID patientId) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isPatientOwner = patientId.equals(currentUserId);

        if (!isAdmin && !isPatientOwner) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

}
