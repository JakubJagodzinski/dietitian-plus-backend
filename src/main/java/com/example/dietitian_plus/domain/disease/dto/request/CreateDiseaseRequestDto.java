package com.example.dietitian_plus.domain.disease.dto.request;

import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"disease_name", "description"})
public class CreateDiseaseRequestDto {

    @Schema(
            description = "Unique name of the disease",
            example = "Diabetes",
            maxLength = 100
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("disease_name")
    private String diseaseName;

    @Schema(
            description = "Optional description of the disease",
            example = "Chronic back pain",
            maxLength = 1_000,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 1_000)
    @JsonProperty("description")
    private String description;

}
