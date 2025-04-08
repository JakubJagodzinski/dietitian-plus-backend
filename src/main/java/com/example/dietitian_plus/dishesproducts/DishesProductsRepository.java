package com.example.dietitian_plus.dishesproducts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishesProductsRepository extends JpaRepository<DishesProducts, Long> {

    List<DishesProducts> findByDish_DishId(Long dishId);

}
