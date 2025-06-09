package com.example.dietitian_plus.domain.meal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByPatient_Id(Long patientId);

    List<Meal> findAllByDietitian_Id(Long dietitianId);

}
