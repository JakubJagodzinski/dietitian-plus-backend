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
public class PatientDiseaseAccessManager {

    private final SecurityUtils securityUtils;

    public void checkCanReadPatientDiseases(Patient patient) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isPatientSelfRequest = patient.getUserId().equals(currentUserId);
        boolean isPatientDietitianRequest = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(currentUserId);

        if (!isAdminRequest && !isPatientSelfRequest && !isPatientDietitianRequest) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanAssignDiseaseToPatient(UUID patientId) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isPatientSelfRequest = patientId.equals(currentUserId);

        if (!isAdminRequest && !isPatientSelfRequest) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanUnassignDiseaseFromPatient(UUID patientId) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isPatientSelfRequest = patientId.equals(currentUserId);

        if (!isAdminRequest && !isPatientSelfRequest) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

}
