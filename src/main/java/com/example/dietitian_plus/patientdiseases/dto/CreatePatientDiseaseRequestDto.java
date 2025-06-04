package com.example.dietitian_plus.patientdiseases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientDiseaseRequestDto {

    @JsonProperty("patient_id")
    private Long patientId;

    @JsonProperty("disease_id")
    private Long diseaseId;

}
