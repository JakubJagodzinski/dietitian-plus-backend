package com.example.dietitian_plus.domain.unit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUnitRequestDto {

    @JsonProperty("unit_name")
    private String unitName;

    private Float grams;

}
