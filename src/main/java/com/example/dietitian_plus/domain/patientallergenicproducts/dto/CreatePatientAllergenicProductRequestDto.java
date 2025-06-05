package com.example.dietitian_plus.domain.patientallergenicproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePatientAllergenicProductRequestDto {

    @JsonProperty("patient_id")
    private Long patientId;

    @JsonProperty("product_id")
    private Long productId;

}
