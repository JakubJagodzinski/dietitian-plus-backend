package com.example.dietitian_plus.dish.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishRequestDto {

    private Long dietitianId;

    private String dishName;

}
