package com.example.dietitian_plus.domain.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PatientResponseDto {

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private Double height;

    @JsonProperty("starting_weight")
    private Double startingWeight;

    @JsonProperty("current_weight")
    private Double currentWeight;

    private Double pal;

    private LocalDate birthdate;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

}
