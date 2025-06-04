package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByDietitian(Dietitian dietitian);

}
