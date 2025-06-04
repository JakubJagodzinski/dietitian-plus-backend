package com.example.dietitian_plus.domain.dish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishRequestDto {

    @JsonProperty("dietitian_id")
    private Long dietitianId;

    @JsonProperty("dish_name")
    private String dishName;

}
