package com.example.dietitian_plus.domain.dishesproducts.dto.request;

import com.example.dietitian_plus.common.constants.messages.UnitMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDishProductRequestDto {

    @Schema(
            description = "New unit id",
            example = "1",
            nullable = true
    )
    @JsonProperty("unit_id")
    private Long unitId;

    @Schema(
            description = "New unit count",
            example = "0.5",
            nullable = true
    )
    @DecimalMin(value = "0.001", message = UnitMessages.UNIT_COUNT_MUST_BE_POSITIVE)
    @JsonProperty("unit_count")
    private Double unitCount;

}
