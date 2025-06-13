package com.example.dietitian_plus.domain.patientdiseases;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientDiseaseRepository extends JpaRepository<PatientDisease, PatientDiseaseId> {

    List<PatientDisease> findAllByPatient_UserId(Long patientId);

}
