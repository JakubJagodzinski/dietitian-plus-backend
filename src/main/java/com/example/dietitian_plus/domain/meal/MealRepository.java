package com.example.dietitian_plus.domain.meal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findAllByPatient_UserId(UUID patientId);

    List<Meal> findAllByDietitian_UserId(UUID dietitianId);

    List<Meal> findAllByPatient_UserIdAndDatetimeBetween(UUID patientId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
