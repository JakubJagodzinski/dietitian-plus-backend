package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.domain.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByPatient(Patient patient);

}
