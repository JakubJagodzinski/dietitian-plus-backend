package com.example.dietitian_plus.meal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MealDto {

    private Long mealId;

    private LocalDateTime datetime;

    private Long userId;

    private Long dietitianId;

}
