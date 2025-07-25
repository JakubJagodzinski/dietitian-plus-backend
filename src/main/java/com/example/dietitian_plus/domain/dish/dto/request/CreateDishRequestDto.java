package com.example.dietitian_plus.domain.dish.dto.request;

import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.example.dietitian_plus.domain.dishesproducts.dto.request.AddProductToDishRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"dish_name", "is_template", "is_public", "recipe", "dietitian_id", "products"})
public class CreateDishRequestDto {

    @Schema(
            description = "Name of the dish, doesn't need to be unique",
            example = "Spaghetti bolognese",
            maxLength = 100
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("dish_name")
    private String dishName;

    @Schema(
            description = "Indicates whether dish is template or not",
            example = "false",
            defaultValue = "false"
    )
    @JsonProperty("is_template")
    private boolean isTemplate = false;

    @Schema(
            description = "Indicates whether dish is public for read only access or not",
            example = "false",
            defaultValue = "false"
    )
    @JsonProperty("is_public")
    private boolean isPublic = false;

    @Schema(
            description = "Optional detailed dish preparation instructions",
            example = "1. Chop the vegetables.\n2. Heat oil in a pan.\n3. Add spices and stir-fry for 5 minutes.",
            maxLength = 1_000,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 1_000)
    @JsonProperty("recipe")
    private String recipe;

    @Schema(
            description = "Id of dish owner",
            example = "123e4567-e89b-12d3-a456-426614174000",
            type = "string",
            format = "uuid"
    )
    @NotNull
    @JsonProperty("dietitian_id")
    private UUID dietitianId;

    @Schema(
            description = "Optional list of product ids to add to dish with unit type and unit count",
            nullable = true
    )
    @JsonProperty("products")
    private List<AddProductToDishRequestDto> products;

}
