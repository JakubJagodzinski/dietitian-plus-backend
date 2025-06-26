package com.example.dietitian_plus.domain.patientdislikedproducts.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignDislikedProductToPatientRequestDto {

    @Schema(
            description = "Id of product to be assigned to patient as disliked",
            example = "1"
    )
    @NotNull
    @JsonProperty("product_id")
    private Long productId;

}
