package com.example.dietitian_plus.domain.patient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findAllByDietitian_Id(Long dietitianId);

}
