package com.example.dietitian_plus.domain.meal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMealRequestDto {

    @JsonProperty("patient_id")
    private Long patientId;

    @JsonProperty("dietitian_id")
    private Long dietitianId;

    private LocalDateTime datetime;

}
