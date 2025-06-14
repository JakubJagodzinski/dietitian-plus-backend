package com.example.dietitian_plus.auth.access.manager;

import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.mealsdishes.MealDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DishAccessManager {

    private final SecurityUtils securityUtils;

    private final MealDishRepository mealDishRepository;

    public void checkCanAccessDish(Dish dish) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();
        UUID dietitianId = dish.getDietitian().getUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dietitianId.equals(currentUserId);
        boolean existsInPatientMeals = mealDishRepository.existsByMeal_Patient_UserIdAndDish_Dietitian_UserId(currentUserId, dietitianId);
        boolean isDishPublic = dish.getIsPublic();

        if (!isAdmin && !isDietitianOwner && !existsInPatientMeals && !isDishPublic) {
            throw new AccessDeniedException(DishMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DISH);
        }
    }

    public void checkCanGetDietitianAllDishes(UUID dietitianId) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dietitianId.equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(DishMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DISH);
        }
    }

    public void checkCanUpdateDish(Dish dish) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dish.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(DishMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DISH);
        }
    }

    public void checkCanDeleteDish(Dish dish) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdmin = securityUtils.isAdmin();
        boolean isDietitianOwner = dish.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(DishMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DISH);
        }
    }

}
