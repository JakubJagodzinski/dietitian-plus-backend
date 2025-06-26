package com.example.dietitian_plus.domain.patientdislikedproducts.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"patient_id", "product_id"})
public class PatientDislikedProductResponseDto {

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("product_id")
    private Long productId;

}
