package com.example.dietitian_plus.dishesproducts;

import com.example.dietitian_plus.dish.Dish;
import com.example.dietitian_plus.product.Product;
import com.example.dietitian_plus.unit.Unit;
import jakarta.persistence.*;

@Entity
@Table(name = "dishes_products")
public class DishesProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    private Float unit_count;

}
