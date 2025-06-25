package com.example.dietitian_plus.domain.patientdiseases.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignDiseaseToPatientRequestDto {

    @JsonProperty("disease_id")
    private Long diseaseId;

}
