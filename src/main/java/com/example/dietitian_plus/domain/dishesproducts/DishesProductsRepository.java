package com.example.dietitian_plus.domain.dishesproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishesProductsRepository extends JpaRepository<DishesProducts, Long> {

    List<DishesProducts> findByDish_DishId(Long dishId);

}
