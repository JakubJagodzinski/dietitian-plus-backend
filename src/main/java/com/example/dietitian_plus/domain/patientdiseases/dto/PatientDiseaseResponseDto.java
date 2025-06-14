package com.example.dietitian_plus.domain.patientdiseases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PatientDiseaseResponseDto {

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("disease_id")
    private Long diseaseId;

}
