package com.example.dietitian_plus.auth.access.manager;

import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.patient.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientAccessManager {

    private final SecurityUtils securityUtils;

    public void checkCanViewPatient(Patient patient) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isPatientOwner = patient.getUserId().equals(currentUserId);
        boolean isPatientDietitianOwner = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isPatientOwner && !isPatientDietitianOwner) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanViewDietitianPatients(UUID dietitianId) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitian = dietitianId.equals(currentUserId);

        if (!isAdmin && !isDietitian) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DIETITIAN_PATIENTS);
        }
    }

    public void checkCanUpdatePatient(Patient patient) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isPatientOwner = patient.getUserId().equals(currentUserId);

        if (!isAdmin && !isPatientOwner) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanAssignDietitianToPatient(Dietitian dietitian) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dietitian.getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_ASSIGN_THIS_DIETITIAN_TO_PATIENT);
        }
    }

    public void checkCanUnassignDietitianFromPatient(Patient patient) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isPatientOwner = patient.getUserId().equals(currentUserId);
        boolean isDietitianOwner = patient.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isPatientOwner && !isDietitianOwner) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanDeletePatient(Patient patient) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        boolean isAdmin = securityUtils.isAdmin();
        boolean isPatientOwner = patient.getUserId().equals(currentUserId);

        if (!isAdmin && !isPatientOwner) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

}
