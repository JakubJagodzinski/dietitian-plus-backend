package com.example.dietitian_plus.domain.mealsdishes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MealDishRepository extends JpaRepository<MealDish, MealDishId> {

    boolean existsByMeal_Patient_UserIdAndDish_Dietitian_UserId(UUID patientId, UUID dietitianId);

    List<MealDish> findAllByMeal_MealId(Long mealId);

    void deleteAllByDish_Dietitian_UserId(UUID dietitianId);

}
