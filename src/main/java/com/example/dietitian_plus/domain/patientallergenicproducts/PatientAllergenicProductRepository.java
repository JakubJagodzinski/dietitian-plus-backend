package com.example.dietitian_plus.domain.patientallergenicproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientAllergenicProductRepository extends JpaRepository<PatientAllergenicProduct, PatientAllergenicProductId> {

    List<PatientAllergenicProduct> findAllById_PatientId(Long patientId);

}
