package com.example.dietitian_plus.domain.mealsdishes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDishId {

    @Column(name = "meal_id")
    private Long mealId;

    @Column(name = "dish_id")
    private Long dishId;

}
