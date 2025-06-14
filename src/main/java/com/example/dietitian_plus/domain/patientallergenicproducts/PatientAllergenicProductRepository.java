package com.example.dietitian_plus.domain.patientallergenicproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PatientAllergenicProductRepository extends JpaRepository<PatientAllergenicProduct, PatientAllergenicProductId> {

    List<PatientAllergenicProduct> findAllByPatient_UserId(UUID patientId);

}
