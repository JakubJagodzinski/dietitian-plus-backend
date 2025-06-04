package com.example.dietitian_plus.domain.mealsdishes;

import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.meal.Meal;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "meals_dishes")
@Data
public class MealsDishes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dish dish;

}
