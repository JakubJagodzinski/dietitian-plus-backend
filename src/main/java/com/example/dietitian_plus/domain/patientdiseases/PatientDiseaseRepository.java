package com.example.dietitian_plus.domain.patientdiseases;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientDiseaseRepository extends JpaRepository<PatientDisease, PatientDiseaseId> {

    List<PatientDisease> findAllById_PatientId(Long patientId);

    List<PatientDisease> findAllById_DiseaseId(Long diseaseId);

    boolean existsById(PatientDiseaseId id);

}
