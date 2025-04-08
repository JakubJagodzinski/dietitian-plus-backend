package com.example.dietitian_plus.meal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateMealDto {

    private Long patientId;

    private Long dietitianId;

    private LocalDateTime datetime;

}
