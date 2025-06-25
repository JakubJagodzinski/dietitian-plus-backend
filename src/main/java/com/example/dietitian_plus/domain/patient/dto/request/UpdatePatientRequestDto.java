package com.example.dietitian_plus.domain.patient.dto.request;

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

    private Double height;

    @JsonProperty("starting_weight")
    private Double startingWeight;

    @JsonProperty("current_weight")
    private Double currentWeight;

    private Double pal;

    private LocalDate birthdate;

}
