package com.example.dietitian_plus.mealsdishes;

import com.example.dietitian_plus.dish.Dish;
import com.example.dietitian_plus.meal.Meal;
import jakarta.persistence.*;

@Entity
@Table(name = "meals_dishes")
public class MealsDishes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal mealId;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dishId;

}
