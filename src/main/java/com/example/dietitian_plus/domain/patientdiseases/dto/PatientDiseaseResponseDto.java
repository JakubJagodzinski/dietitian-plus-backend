package com.example.dietitian_plus.domain.patientdiseases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientDiseaseResponseDto {

    @JsonProperty("patient_id")
    private Long patientId;

    @JsonProperty("disease_id")
    private Long diseaseId;

}
