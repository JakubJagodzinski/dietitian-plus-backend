package com.example.dietitian_plus.dish;

import com.example.dietitian_plus.dietitian.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findByDietitian(Dietitian dietitian);

}
