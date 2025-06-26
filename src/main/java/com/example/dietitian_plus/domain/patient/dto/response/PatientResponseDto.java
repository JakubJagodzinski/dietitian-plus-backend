package com.example.dietitian_plus.domain.patient.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"patient_id", "first_name", "last_name", "height", "starting_weight", "current_weight", "pal", "birthdate", "is_active", "dietitian_id"})
public class PatientResponseDto {

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("height")
    private Double height;

    @JsonProperty("starting_weight")
    private Double startingWeight;

    @JsonProperty("current_weight")
    private Double currentWeight;

    @JsonProperty("pal")
    private Double pal;

    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

}
