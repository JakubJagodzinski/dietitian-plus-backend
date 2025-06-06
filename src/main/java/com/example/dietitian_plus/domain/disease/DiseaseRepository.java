package com.example.dietitian_plus.domain.disease;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    boolean existsByDiseaseName(String diseaseName);

}
