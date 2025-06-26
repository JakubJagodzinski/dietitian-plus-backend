package com.example.dietitian_plus.domain.disease.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"disease_id", "disease_name", "description"})
public class DiseaseResponseDto {

    @JsonProperty("disease_id")
    private Long diseaseId;

    @JsonProperty("disease_name")
    private String diseaseName;

    private String description;

}
