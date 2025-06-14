package com.example.dietitian_plus.domain.patientdiseases;

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
public class PatientDiseaseId {

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "disease_id")
    private Long diseaseId;

}
