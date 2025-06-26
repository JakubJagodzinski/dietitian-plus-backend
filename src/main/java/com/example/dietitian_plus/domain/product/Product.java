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

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(nullable = false)
    private Double kcal = 0.0;

    @Column(nullable = false)
    private Double fats = 0.0;

    @Column(nullable = false)
    private Double carbs = 0.0;

    @Column(nullable = false)
    private Double protein = 0.0;

    @Column(nullable = false)
    private Double fiber = 0.0;

    @Column(name = "glycemic_index", nullable = false)
    private Double glycemicIndex = 0.0;

    @Column(name = "glycemic_load", nullable = false)
    private Double glycemicLoad = 0.0;

}
