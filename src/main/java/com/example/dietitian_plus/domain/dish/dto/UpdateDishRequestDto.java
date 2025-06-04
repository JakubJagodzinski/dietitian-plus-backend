package com.example.dietitian_plus.domain.dish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDishRequestDto {

    @JsonProperty("is_visible")
    private Boolean isVisible;

    @JsonProperty("is_public")
    private Boolean isPublic;

    @JsonProperty("dish_name")
    private String dishName;

    private String recipe;

}
