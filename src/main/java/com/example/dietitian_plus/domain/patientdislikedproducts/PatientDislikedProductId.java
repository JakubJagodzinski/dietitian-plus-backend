package com.example.dietitian_plus.domain.patientdislikedproducts;

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
public class PatientDislikedProductId {

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "product_id")
    private Long productId;

}
