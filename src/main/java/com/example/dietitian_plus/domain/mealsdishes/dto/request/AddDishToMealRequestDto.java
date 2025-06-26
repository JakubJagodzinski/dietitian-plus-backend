package com.example.dietitian_plus.domain.mealsdishes.dto.request;

import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"dish_id", "dish_quantity"})
public class AddDishToMealRequestDto {

    @Schema(
            description = "Id of the dish to be added to meal",
            example = "1"
    )
    @NotNull
    @JsonProperty("dish_id")
    private Long dishId;

    @Schema(
            description = "Quantity of dishes added to meal",
            example = "1",
            minimum = "1"
    )
    @NotNull
    @Positive(message = DishMessages.DISH_QUANTITY_MUST_BE_POSITIVE)
    @JsonProperty("dish_quantity")
    private Integer dishQuantity;

}
