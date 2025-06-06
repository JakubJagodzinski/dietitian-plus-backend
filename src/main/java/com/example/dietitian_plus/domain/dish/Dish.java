package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dishes")
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

}
