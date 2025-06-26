package com.example.dietitian_plus.domain.product.dto.request;

import com.example.dietitian_plus.common.constants.messages.ProductMessages;
import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductRequestDto {

    @Schema(
            description = "New name of the product, doesn't have to be unique",
            example = "Apple",
            maxLength = 100,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 100)
    @JsonProperty("product_name")
    private String productName;

    @Schema(
            description = "New value of kcal in product",
            example = "79",
            minimum = "0",
            defaultValue = "0",
            nullable = true
    )
    @PositiveOrZero(message = ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE)
    @JsonProperty("kcal")
    private Double kcal = 0.0;

    @Schema(
            description = "New value of fats in product",
            example = "79",
            minimum = "0",
            defaultValue = "0",
            nullable = true
    )
    @PositiveOrZero(message = ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE)
    @JsonProperty("fats")
    private Double fats = 0.0;

    @Schema(
            description = "New value of carbs in product",
            example = "79",
            minimum = "0",
            defaultValue = "0",
            nullable = true
    )
    @PositiveOrZero(message = ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE)
    @JsonProperty("carbs")
    private Double carbs = 0.0;

    @Schema(
            description = "New value of protein in product",
            example = "79",
            minimum = "0",
            defaultValue = "0",
            nullable = true
    )
    @PositiveOrZero(message = ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE)
    @JsonProperty("protein")
    private Double protein = 0.0;

    @Schema(
            description = "New value of fiber in product",
            example = "79",
            minimum = "0",
            defaultValue = "0",
            nullable = true
    )
    @PositiveOrZero(message = ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE)
    @JsonProperty("fiber")
    private Double fiber = 0.0;

    @Schema(
            description = "Product new glycemic index",
            example = "79",
            minimum = "0",
            defaultValue = "0",
            nullable = true
    )
    @PositiveOrZero(message = ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE)
    @JsonProperty("glycemic_index")
    private Double glycemicIndex = 0.0;

    @Schema(
            description = "Product new glycemic load",
            example = "79",
            minimum = "0",
            defaultValue = "0",
            nullable = true
    )
    @PositiveOrZero(message = ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE)
    @JsonProperty("glycemic_load")
    private Double glycemicLoad = 0.0;

}
