package com.example.dietitian_plus.domain.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePatientRequestDto {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private Float height;

    @JsonProperty("starting_weight")
    private Float startingWeight;

    @JsonProperty("current_weight")
    private Float currentWeight;

    private Float pal;

    private LocalDate birthdate;

}
