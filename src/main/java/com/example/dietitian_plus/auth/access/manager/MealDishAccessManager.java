package com.example.dietitian_plus.auth.access.manager;

import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.MealMessages;
import com.example.dietitian_plus.domain.meal.Meal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MealDishAccessManager {

    private final SecurityUtils securityUtils;

    private final MealAccessManager mealAccessManager;

    public void checkCanReadMealAllDishes(Meal meal) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianMealOwnerRequest = mealAccessManager.isDietitianMealOwner(meal);
        boolean isDietitianPatientRequest = meal.getPatient().getUserId().equals(currentUserId);

        if (!isAdminRequest && !isDietitianMealOwnerRequest && !isDietitianPatientRequest) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanAddDishToMeal(Meal meal) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianMealOwnerRequest = mealAccessManager.isDietitianMealOwner(meal);

        if (!isAdminRequest && !isDietitianMealOwnerRequest) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

    public void checkCanRemoveDishFromMeal(Meal meal) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianMealOwnerRequest = mealAccessManager.isDietitianMealOwner(meal);

        if (!isAdminRequest && !isDietitianMealOwnerRequest) {
            throw new AccessDeniedException(MealMessages.YOU_HAVE_NO_ACCESS_TO_THIS_MEAL);
        }
    }

}
