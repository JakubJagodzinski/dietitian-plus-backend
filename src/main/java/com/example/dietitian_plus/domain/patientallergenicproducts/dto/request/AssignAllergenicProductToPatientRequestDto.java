package com.example.dietitian_plus.domain.patientallergenicproducts.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignAllergenicProductToPatientRequestDto {

    @Schema(
            description = "Id of product to be assigned to patient as allergenic",
            example = "1"
    )
    @NotNull
    @JsonProperty("product_id")
    private Long productId;

}
