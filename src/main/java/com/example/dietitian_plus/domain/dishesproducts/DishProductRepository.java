package com.example.dietitian_plus.domain.dishesproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishProductRepository extends JpaRepository<DishProduct, Long> {

    List<DishProduct> findAllByDish_DishId(Long dishId);

    List<DishProduct> findAllByProduct_ProductId(Long productId);

}
