package com.example.dietitian_plus.domain.dishesproducts.dto.request;

import com.example.dietitian_plus.common.constants.messages.UnitMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddProductToDishRequestDto {

    @Schema(
            description = "Id of the product to add to dish, you can add the same product to one dish multiple times, each in its own unit",
            example = "1"
    )
    @NotNull
    @JsonProperty("product_id")
    private Long productId;

    @Schema(
            description = "Unit id",
            example = "1"
    )
    @NotNull
    @JsonProperty("unit_id")
    private Long unitId;

    @Schema(
            description = "Unit count",
            example = "0.5"
    )
    @NotNull
    @DecimalMin(value = "0.001", message = UnitMessages.UNIT_COUNT_MUST_BE_POSITIVE)
    @JsonProperty("unit_count")
    private Double unitCount;

}
