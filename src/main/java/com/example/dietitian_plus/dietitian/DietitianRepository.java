package com.example.dietitian_plus.dietitian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietitianRepository extends JpaRepository<Dietitian, Long> {
}
