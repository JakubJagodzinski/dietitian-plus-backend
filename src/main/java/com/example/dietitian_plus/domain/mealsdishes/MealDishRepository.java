package com.example.dietitian_plus.domain.mealsdishes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MealDishRepository extends JpaRepository<MealDish, Long> {

    List<MealDish> findAllByMeal_MealId(Long mealId);

    Optional<MealDish> findByMeal_MealIdAndDish_DishId(Long mealId, Long dishId);

}
