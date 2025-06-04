package com.example.dietitian_plus.domain.product;

import com.example.dietitian_plus.domain.dishesproducts.DishesProducts;
import com.example.dietitian_plus.domain.patient.Patient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Float kcal = 0.0f;

    private Float fats = 0.0f;

    private Float carbs = 0.0f;

    private Float protein = 0.0f;

    private Float fiber = 0.0f;

    @ManyToMany(mappedBy = "allergenicProducts")
    @JsonBackReference
    private final Set<Patient> patientsWithAllergy = new HashSet<>();

    @ManyToMany(mappedBy = "dislikedProducts")
    @JsonBackReference
    private final Set<Patient> dislikedPatients = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private final List<DishesProducts> dishesProducts = new ArrayList<>();

}
