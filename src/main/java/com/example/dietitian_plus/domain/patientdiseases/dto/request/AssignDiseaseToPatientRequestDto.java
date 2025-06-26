package com.example.dietitian_plus.domain.patientdiseases.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignDiseaseToPatientRequestDto {

    @Schema(
            description = "Id of disease to be assigned to patient",
            example = "1"
    )
    @NotNull
    @JsonProperty("disease_id")
    private Long diseaseId;

}
