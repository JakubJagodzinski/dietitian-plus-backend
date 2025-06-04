package com.example.dietitian_plus.domain.unit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitResponseDto {

    private Long unitId;

    private String unitName;

    private Float grams;

}
