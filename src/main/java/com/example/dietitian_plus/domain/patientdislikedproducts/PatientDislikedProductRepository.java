package com.example.dietitian_plus.domain.patientdislikedproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PatientDislikedProductRepository extends JpaRepository<PatientDislikedProduct, PatientDislikedProductId> {

    List<PatientDislikedProduct> findAllByPatient_UserId(UUID patientId);

}
