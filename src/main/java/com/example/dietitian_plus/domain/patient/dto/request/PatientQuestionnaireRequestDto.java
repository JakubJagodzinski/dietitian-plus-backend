package com.example.dietitian_plus.domain.patient.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PatientQuestionnaireRequestDto {

    private Double height;

    @JsonProperty("starting_weight")
    private Double startingWeight;

    private Double pal;

    private LocalDate birthdate;

    private String gender;

}
