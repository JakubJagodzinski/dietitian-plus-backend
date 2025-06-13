package com.example.dietitian_plus.domain.mealsdishes;

import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.meal.Meal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "meals_dishes")
public class MealDish {

    @EmbeddedId
    private MealDishId mealDishId;

    @ManyToOne
    @MapsId("mealId")
    @JoinColumn(name = "meal_id", foreignKey = @ForeignKey(name = "fk_meals_dishes_meal_id"))
    private Meal meal;

    @ManyToOne
    @MapsId("dishId")
    @JoinColumn(name = "dish_id", foreignKey = @ForeignKey(name = "fk_meals_dishes_dish_id"))
    private Dish dish;

    @Column(name = "dish_quantity")
    private Long dishQuantity;

}
