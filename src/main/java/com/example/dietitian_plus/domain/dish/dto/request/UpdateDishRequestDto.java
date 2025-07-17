package com.example.dietitian_plus.domain.dish.dto.request;

import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.example.dietitian_plus.domain.dishesproducts.dto.request.AddProductToDishRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"dish_name", "is_template", "is_public", "recipe"})
public class UpdateDishRequestDto {

    @Schema(
            description = "New name of the dish, doesn't need to be unique",
            example = "Spaghetti bolognese",
            maxLength = 100,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 100)
    @JsonProperty("dish_name")
    private String dishName;

    @Schema(
            description = "Indicates whether dish is template or not",
            example = "false",
            nullable = true
    )
    @JsonProperty("is_template")
    private Boolean isTemplate;

    @Schema(
            description = "Indicates whether dish is public for read only access or not",
            example = "false",
            nullable = true
    )
    @JsonProperty("is_public")
    private Boolean isPublic;

    @Schema(
            description = "New detailed dish preparation instructions",
            example = "1. Chop the vegetables.\n2. Heat oil in a pan.\n3. Add spices and stir-fry for 5 minutes.",
            maxLength = 1_000,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 1_000)
    @JsonProperty("recipe")
    private String recipe;

    @Schema(
            description = "Optional list of product ids that will replace current products in dish with unit type and unit count",
            nullable = true
    )
    @JsonProperty("products")
    private List<AddProductToDishRequestDto> products;

}
