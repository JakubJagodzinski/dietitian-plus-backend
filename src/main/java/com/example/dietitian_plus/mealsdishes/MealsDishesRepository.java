package com.example.dietitian_plus.mealsdishes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealsDishesRepository extends JpaRepository<MealsDishes, Long> {
}
