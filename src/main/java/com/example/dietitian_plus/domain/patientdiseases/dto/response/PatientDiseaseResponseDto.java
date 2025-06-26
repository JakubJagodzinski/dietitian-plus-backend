package com.example.dietitian_plus.domain.patientdiseases.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"patient_id", "disease_id"})
public class PatientDiseaseResponseDto {

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("disease_id")
    private Long diseaseId;

}
