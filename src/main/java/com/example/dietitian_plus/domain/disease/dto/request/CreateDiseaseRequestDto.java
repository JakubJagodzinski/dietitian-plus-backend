package com.example.dietitian_plus.domain.disease.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateDiseaseRequestDto {

    @JsonProperty("disease_name")
    private String diseaseName;

    private String description;

}
