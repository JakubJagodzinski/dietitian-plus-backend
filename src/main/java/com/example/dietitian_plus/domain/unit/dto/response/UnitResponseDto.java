package com.example.dietitian_plus.domain.unit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"unit_id", "unit_name", "grams"})
public class UnitResponseDto {

    @JsonProperty("unit_id")
    private Long unitId;

    @JsonProperty("unit_name")
    private String unitName;

    @JsonProperty("grams")
    private Double grams;

}
