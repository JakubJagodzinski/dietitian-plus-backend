package com.example.dietitian_plus.domain.patient.dto.request;

import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"first_name", "last_name", "height", "current_weight", "pal"})
public class UpdatePatientRequestDto {

    @Schema(
            description = "Patient's first name",
            example = "John",
            maxLength = 100,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 100)
    @JsonProperty("first_name")
    private String firstName;

    @Schema(
            description = "Patient's last name",
            example = "Smith",
            maxLength = 100,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 100)
    @JsonProperty("last_name")
    private String lastName;

    @Schema(
            description = "Patient's current height in cm",
            example = "188",
            nullable = true
    )
    @Positive(message = PatientMessages.PATIENT_HEIGHT_MUST_BE_POSITIVE)
    @JsonProperty("height")
    private Double height;

    @Schema(
            description = "Patient's current weight in kg",
            example = "67.5",
            nullable = true
    )
    @Positive(message = PatientMessages.PATIENT_WEIGHT_MUST_BE_POSITIVE)
    @JsonProperty("current_weight")
    private Double currentWeight;

    @Schema(
            description = "Patient's physical activity level",
            example = "2.0",
            minimum = "1.0",
            maximum = "3.0",
            nullable = true
    )
    @DecimalMin(value = "1.0", message = PatientMessages.PAL_MUST_BE_AT_LEAST_1_0)
    @DecimalMax(value = "3.0", message = PatientMessages.PAL_MUST_BE_AT_MOST_3_0)
    @JsonProperty("pal")
    private Double pal;

}
