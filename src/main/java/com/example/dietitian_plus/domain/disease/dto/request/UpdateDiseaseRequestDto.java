package com.example.dietitian_plus.domain.disease.dto.request;

import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDiseaseRequestDto {

    @Schema(
            description = "New unique name of the disease",
            example = "Diabetes",
            maxLength = 100,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 100)
    @JsonProperty("disease_name")
    private String diseaseName;

    @Schema(
            description = "New description of the disease",
            example = "Chronic back pain",
            maxLength = 1_000,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 1_000)
    @JsonProperty("description")
    private String description;

}
