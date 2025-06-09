package com.example.dietitian_plus.domain.mealsdishes;

import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.meal.Meal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "meals_dishes")
public class MealDish {

    @EmbeddedId
    private MealDishId id;

    @ManyToOne
    @MapsId("mealId")
    @JoinColumn(name = "meal_id", foreignKey = @ForeignKey(name = "fk_meal_dish_meal"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Meal meal;

    @ManyToOne
    @MapsId("dishId")
    @JoinColumn(name = "dish_id", foreignKey = @ForeignKey(name = "fk_meal_dish_dish"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dish dish;

    @Column(name = "dish_quantity")
    private Long dishQuantity;

}
