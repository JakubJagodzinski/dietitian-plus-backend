package com.example.dietitian_plus.domain.patient.dto.request;

import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PatientQuestionnaireRequestDto {

    @Schema(
            description = "Patient's current height in cm",
            example = "188"
    )
    @NotNull
    @Positive(message = PatientMessages.PATIENT_HEIGHT_MUST_BE_POSITIVE)
    @JsonProperty("height")
    private Double height;

    @Schema(
            description = "Patient's starting weight in kg",
            example = "67.5"
    )
    @NotNull
    @Positive(message = PatientMessages.PATIENT_WEIGHT_MUST_BE_POSITIVE)
    @JsonProperty("starting_weight")
    private Double startingWeight;

    @Schema(
            description = "Patient's physical activity level",
            example = "2.0",
            minimum = "1.0",
            maximum = "3.0"
    )
    @NotNull
    @DecimalMin(value = "1.0", message = PatientMessages.PAL_MUST_BE_AT_LEAST_1_0)
    @DecimalMax(value = "3.0", message = PatientMessages.PAL_MUST_BE_AT_MOST_3_0)
    @JsonProperty("pal")
    private Double pal;

    @Schema(
            description = "Patient's birthdate in ISO format",
            example = "1985-08-15",
            type = "string",
            format = "date"
    )
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = PatientMessages.BIRTHDATE_MUST_BE_FROM_THE_PAST)
    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @Schema(
            description = "Patient's gender",
            example = "male",
            allowableValues = {"male", "female", "other"}
    )
    @NotNull
    @JsonProperty("gender")
    private String gender;

}
