package com.example.dietitian_plus.domain.patientdiseases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientDiseaseRepository extends JpaRepository<PatientDisease, PatientDiseaseId> {

    List<PatientDisease> findById_PatientId(Long patientId);

    List<PatientDisease> findById_DiseaseId(Long diseaseId);

    boolean existsById(PatientDiseaseId id);

}
