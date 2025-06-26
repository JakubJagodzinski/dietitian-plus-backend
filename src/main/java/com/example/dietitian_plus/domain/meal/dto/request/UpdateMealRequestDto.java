package com.example.dietitian_plus.domain.meal.dto.request;

import com.example.dietitian_plus.common.constants.messages.MealMessages;
import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMealRequestDto {

    @Schema(
            description = "New name of the meal",
            example = "Lunch",
            maxLength = 100
    )
    @NotEmptyIfPresent
    @Size(max = 100)
    @JsonProperty("meal_name")
    private String mealName;

    @Schema(
            description = "New day and start time of the meal",
            example = "2025-06-26T14:30:00",
            nullable = true
    )
    @FutureOrPresent(message = MealMessages.MEAL_DATETIME_CANNOT_BE_FROM_THE_PAST)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("datetime")
    private LocalDateTime datetime;

}
