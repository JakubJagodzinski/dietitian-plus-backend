package com.example.dietitian_plus.dish;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDishDto {

    private Boolean isVisible;

    private Boolean isPublic;

    private Long dietitianId;

    private String dishName;

    private String recipe;

}
