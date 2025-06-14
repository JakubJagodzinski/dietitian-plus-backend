package com.example.dietitian_plus.domain.dish;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findAllByDietitian_UserId(UUID dietitianId);

}
