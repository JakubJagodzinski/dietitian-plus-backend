package com.example.dietitian_plus.domain.patientallergenicproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PatientAllergenicProductResponseDto {

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("product_id")
    private Long productId;

}
