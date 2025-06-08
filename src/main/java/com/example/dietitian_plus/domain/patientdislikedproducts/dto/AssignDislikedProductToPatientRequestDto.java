package com.example.dietitian_plus.domain.patientdislikedproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignDislikedProductToPatientRequestDto {

    @JsonProperty("patient_id")
    private Long patientId;

    @JsonProperty("product_id")
    private Long productId;

}
