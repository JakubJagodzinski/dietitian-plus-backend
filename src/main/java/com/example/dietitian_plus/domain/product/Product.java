package com.example.dietitian_plus.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private Float kcal = 0.0f;

    @Column(nullable = false)
    private Float fats = 0.0f;

    @Column(nullable = false)
    private Float carbs = 0.0f;

    @Column(nullable = false)
    private Float protein = 0.0f;

    @Column(nullable = false)
    private Float fiber = 0.0f;

    @Column(name = "glycemic_index", nullable = false)
    private Float glycemicIndex = 0.0f;

    @Column(name = "glycemic_load", nullable = false)
    private Float glycemicLoad = 0.0f;

}
