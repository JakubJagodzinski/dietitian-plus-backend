package com.example.dietitian_plus.domain.dishesproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDishProductEntryRequestDto {

    @JsonProperty("unit_id")
    private Long unitId;

    @JsonProperty("unit_count")
    private Double unitCount;

}
