package com.example.dietitian_plus.domain.unit.dto.request;

import com.example.dietitian_plus.common.constants.messages.UnitMessages;
import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUnitRequestDto {

    @Schema(
            description = "New unique unit name",
            example = "Kilogram",
            maxLength = 50,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 50)
    @JsonProperty("unit_name")
    private String unitName;

    @Schema(
            description = "New number of grams per unit",
            example = "5",
            minimum = "0.001",
            nullable = true
    )
    @DecimalMin(value = "0.001", message = UnitMessages.UNIT_GRAMS_MUST_BE_POSITIVE)
    @JsonProperty("grams")
    private Double grams;

}
