package com.example.dietitian_plus.domain.patientdislikedproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientDislikedProductRepository extends JpaRepository<PatientDislikedProduct, PatientDislikedProductId> {

    List<PatientDislikedProduct> findAllById_PatientId(Long patientId);

}
