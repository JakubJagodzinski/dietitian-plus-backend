package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.product.Product;
import com.example.dietitian_plus.domain.unit.Unit;
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
@Table(name = "dishes_products")
public class DishProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Unit unit;

    @Column(nullable = false)
    private Float unitCount = 0.0f;

}
