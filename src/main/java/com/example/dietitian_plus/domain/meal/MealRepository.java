package com.example.dietitian_plus.domain.meal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findAllByPatient_UserId(Long patientId);

    List<Meal> findAllByDietitian_UserId(Long dietitianId);

}
