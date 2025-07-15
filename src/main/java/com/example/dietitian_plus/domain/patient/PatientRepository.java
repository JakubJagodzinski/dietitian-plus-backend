package com.example.dietitian_plus.domain.patient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    List<Patient> findAllByDietitian_UserId(UUID dietitianId);

    Optional<Patient> findByEmail(String email);

    boolean existsByUserIdAndIsQuestionnaireCompletedIsTrue(UUID patientId);

}
