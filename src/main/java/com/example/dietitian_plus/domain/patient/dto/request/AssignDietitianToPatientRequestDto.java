package com.example.dietitian_plus.domain.patient.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AssignDietitianToPatientRequestDto {

    @Schema(
            description = "Id of dietitian to be assigned to patient",
            example = "123e4567-e89b-12d3-a456-426614174000",
            type = "string",
            format = "uuid"
    )
    @NotNull
    @JsonProperty("dietitian_id")
    private UUID dietitianId;

}
