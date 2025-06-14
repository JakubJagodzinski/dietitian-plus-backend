package com.example.dietitian_plus.domain.mealsdishes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MealDishRepository extends JpaRepository<MealDish, MealDishId> {

    List<MealDish> findAllByMeal_MealId(Long mealId);

    @Modifying
    @Query("DELETE FROM MealDish md WHERE md.dish.dietitian.userId = :dietitianId")
    void deleteAllByDietitianId(@Param("dietitianId") UUID dietitianId);

}
