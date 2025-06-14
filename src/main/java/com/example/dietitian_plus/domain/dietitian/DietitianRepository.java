package com.example.dietitian_plus.domain.dietitian;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DietitianRepository extends JpaRepository<Dietitian, UUID> {
}
