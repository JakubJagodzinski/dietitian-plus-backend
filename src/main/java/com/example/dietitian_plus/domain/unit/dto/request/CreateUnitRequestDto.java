package com.example.dietitian_plus.domain.unit.dto.request;

import com.example.dietitian_plus.common.constants.messages.UnitMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"unit_name", "grams"})
public class CreateUnitRequestDto {

    @Schema(
            description = "Unique unit name",
            example = "Kilogram",
            maxLength = 50
    )
    @NotBlank
    @Size(max = 50)
    @JsonProperty("unit_name")
    private String unitName;

    @Schema(
            description = "Number of grams per unit",
            example = "5",
            minimum = "0.001"
    )
    @NotNull
    @DecimalMin(value = "0.001", message = UnitMessages.UNIT_GRAMS_MUST_BE_POSITIVE)
    @JsonProperty("grams")
    private Double grams;

}
