package com.example.dietitian_plus.domain.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PatientQuestionnaireRequestDto {

    private Float height;

    @JsonProperty("starting_weight")
    private Float startingWeight;

    private Float pal;

    private LocalDate birthdate;

}
