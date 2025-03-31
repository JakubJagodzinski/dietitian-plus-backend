package com.example.dietitian_plus.dishesproducts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishesProductsRepository extends JpaRepository<DishesProducts, Long> {
}
