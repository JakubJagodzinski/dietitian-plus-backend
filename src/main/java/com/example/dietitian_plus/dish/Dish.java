package com.example.dietitian_plus.dish;


import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dishesproducts.DishesProducts;
import com.example.dietitian_plus.meal.Meal;
import com.example.dietitian_plus.product.Product;
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

    @Column(name = "is_visible")
    private Boolean isVisible = Boolean.FALSE;

    @Column(name = "is_public")
    private Boolean isPublic = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    @JsonManagedReference
    private Dietitian dietitian;

    @Column(name = "dish_name")
    private String dishName;

    private String recipe = "";

    private Float kcal = 0.0f;

    private Float fats = 0.0f;

    private Float carbs = 0.0f;

    private Float protein = 0.0f;

    private Float fiber = 0.0f;

    @ManyToMany(mappedBy = "dishes")
    @JsonBackReference
    @ToString.Exclude
    private final List<Meal> meals = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "dishes_products", joinColumns = @JoinColumn(name = "dish_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonManagedReference
    @ToString.Exclude
    private final List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "dish")
    @JsonBackReference
    @ToString.Exclude
    private final List<DishesProducts> dishesProducts = new ArrayList<>();

}
