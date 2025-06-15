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

    public void checkCanCreateMeal(Patient patient, Dietitian dietitian) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dietitian.getUserId().equals(currentUserId);
        boolean isPatientDietitian = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(dietitian.getUserId());
        boolean isPatientDietitianOwner = isPatientDietitian && isDietitianOwner;

        if (!isAdmin && !isPatientDietitianOwner) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanAccessMeal(Meal meal) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = meal.getDietitian().getUserId().equals(currentUserId);
        boolean isPatientOwner = meal.getPatient().getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner && !isPatientOwner) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanGetPatientAllMeals(Patient patient) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isPatientDietitian = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(currentUserId);
        boolean isPatientOwner = patient.getUserId().equals(currentUserId);

        if (!isAdmin && !isPatientDietitian && !isPatientOwner) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanGetDietitianAllMeals(Dietitian dietitian) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dietitian.getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanUpdateMeal(Meal meal) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = meal.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanDeleteMeal(Meal meal) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = meal.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

}
