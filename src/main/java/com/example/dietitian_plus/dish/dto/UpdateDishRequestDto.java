package com.example.dietitian_plus.dish.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDishRequestDto {

    private Boolean isVisible;

    private Boolean isPublic;

    private String dishName;

    private String recipe;

}
