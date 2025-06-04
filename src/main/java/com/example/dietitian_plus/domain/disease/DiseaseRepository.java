package com.example.dietitian_plus.domain.disease;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
