package com.example.dietitian_plus.meal;

import com.example.dietitian_plus.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByPatient(Patient patient);

}
