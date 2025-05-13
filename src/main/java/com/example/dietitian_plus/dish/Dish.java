package com.example.dietitian_plus.dish;


import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dishesproducts.DishesProducts;
import com.example.dietitian_plus.meal.Meal;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dishes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private Long dishId;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible = false;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = false;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    @JsonManagedReference
    private Dietitian dietitian;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    private String recipe = "";

    private Float kcal = 0.0f;

    private Float fats = 0.0f;

    private Float carbs = 0.0f;

    private Float protein = 0.0f;

    private Float fiber = 0.0f;

    @OneToMany(mappedBy = "dish")
    @JsonBackReference
    @ToString.Exclude
    private final List<DishesProducts> dishesProducts = new ArrayList<>();

}
