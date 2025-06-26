package com.example.dietitian_plus.domain.meal.dto.request;

import com.example.dietitian_plus.common.constants.messages.MealMessages;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"meal_name", "datetime", "patient_id", "dietitian_id"})
public class CreateMealRequestDto {

    @Schema(
            description = "Name of the meal",
            example = "Lunch",
            maxLength = 100
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("meal_name")
    private String mealName;

    @Schema(
            description = "Day and start time of the meal",
            example = "2025-06-26T14:30:00"
    )
    @NotNull
    @FutureOrPresent(message = MealMessages.MEAL_DATETIME_CANNOT_BE_FROM_THE_PAST)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("datetime")
    private LocalDateTime datetime;

    @Schema(
            description = "Id of the patient the meal is assigned to",
            example = "123e4567-e89b-12d3-a456-426614174000",
            type = "string",
            format = "uuid"
    )
    @NotNull
    @JsonProperty("patient_id")
    private UUID patientId;

    @Schema(
            description = "Id of the dietitian that created this meal",
            example = "123e4567-e89b-12d3-a456-426614174000",
            type = "string",
            format = "uuid"
    )
    @NotNull
    @JsonProperty("dietitian_id")
    private UUID dietitianId;

}
