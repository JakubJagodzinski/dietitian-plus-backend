package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByDietitian(Dietitian dietitian);

}
