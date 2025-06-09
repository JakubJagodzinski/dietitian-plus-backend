package com.example.dietitian_plus.domain.mealsdishes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealDishRepository extends JpaRepository<MealDish, MealDishId> {

    List<MealDish> findAllById_MealId(Long mealId);

}
