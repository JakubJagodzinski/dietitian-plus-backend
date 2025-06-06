package com.example.dietitian_plus.domain.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientResponseDto {

    @JsonProperty("patient_id")
    private Long patientId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private Float height;

    @JsonProperty("starting_weight")
    private Float startingWeight;

    @JsonProperty("current_weight")
    private Float currentWeight;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("dietitian_id")
    private Long dietitianId;

}
