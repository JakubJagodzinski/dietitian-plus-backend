package com.example.dietitian_plus.domain.patientdiseases;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PatientDiseaseRepository extends JpaRepository<PatientDisease, PatientDiseaseId> {

    List<PatientDisease> findAllByPatient_UserId(UUID patientId);

}
