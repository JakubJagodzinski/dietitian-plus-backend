package com.example.dietitian_plus.product;

import com.example.dietitian_plus.dishesproducts.DishesProducts;
import com.example.dietitian_plus.patient.Patient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @ToString.Exclude
    private final Set<Patient> patientsWithAllergy = new HashSet<>();

    @ManyToMany(mappedBy = "dislikedProducts")
    @JsonBackReference
    @ToString.Exclude
    private final Set<Patient> dislikedPatients = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    @ToString.Exclude
    private final List<DishesProducts> dishesProducts = new ArrayList<>();

}
