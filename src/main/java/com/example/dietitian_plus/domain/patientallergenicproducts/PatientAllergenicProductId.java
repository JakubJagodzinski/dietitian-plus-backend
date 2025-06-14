package com.example.dietitian_plus.domain.patientallergenicproducts;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAllergenicProductId {

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "product_id")
    private Long productId;

}
