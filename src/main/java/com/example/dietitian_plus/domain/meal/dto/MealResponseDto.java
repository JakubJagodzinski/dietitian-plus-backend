package com.example.dietitian_plus.domain.meal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MealResponseDto {

    private Long mealId;

    private LocalDateTime datetime;

    private Long patientId;

    private Long dietitianId;

}
