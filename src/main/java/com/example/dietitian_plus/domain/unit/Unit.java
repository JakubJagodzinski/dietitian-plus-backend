package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.domain.dishesproducts.DishesProducts;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "unit_name", nullable = false)
    private String unitName;

    @Column(nullable = false)
    private Float grams;

    @OneToMany(mappedBy = "unit")
    @JsonBackReference
    private final List<DishesProducts> dishesProducts = new ArrayList<>();

}
