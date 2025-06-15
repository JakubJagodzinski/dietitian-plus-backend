package com.example.dietitian_plus.auth.access.manager;

import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.MealMessages;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.meal.Meal;
import com.example.dietitian_plus.domain.patient.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MealAccessManager {

    private final SecurityUtils securityUtils;

    public boolean isDietitianMealOwner(Meal meal) {
        UUID currentUserId = securityUtils.getCurrentUserId();

        return meal.getDietitian().getUserId().equals(currentUserId);
    }

    public void checkCanCreateMeal(Patient patient, Dietitian dietitian) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianMealOwnerRequest = dietitian.getUserId().equals(currentUserId);
        boolean isDietitianAssignedToPatient = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(dietitian.getUserId());
        boolean isPatientDietitianRequest = isDietitianAssignedToPatient && isDietitianMealOwnerRequest;

        if (!isAdminRequest && !isPatientDietitianRequest) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanReadMeal(Meal meal) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianMealOwnerRequest = isDietitianMealOwner(meal);
        boolean isDietitianPatientRequest = meal.getPatient().getUserId().equals(currentUserId);

        if (!isAdminRequest && !isDietitianMealOwnerRequest && !isDietitianPatientRequest) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanReadPatientAllMeals(Patient patient) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isPatientDietitianRequest = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(currentUserId);
        boolean isPatientSelfRequest = patient.getUserId().equals(currentUserId);

        if (!isAdminRequest && !isPatientDietitianRequest && !isPatientSelfRequest) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanReadDietitianAllMeals(Dietitian dietitian) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianSelfRequest = dietitian.getUserId().equals(currentUserId);

        if (!isAdminRequest && !isDietitianSelfRequest) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanUpdateMeal(Meal meal) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianMealOwnerRequest = isDietitianMealOwner(meal);

        if (!isAdminRequest && !isDietitianMealOwnerRequest) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanDeleteMeal(Meal meal) throws AccessDeniedException {
        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianMealOwnerRequest = isDietitianMealOwner(meal);

        if (!isAdmin && !isDietitianMealOwnerRequest) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

}
