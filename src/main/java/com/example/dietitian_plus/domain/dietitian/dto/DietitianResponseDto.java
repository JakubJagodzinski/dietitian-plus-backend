package com.example.dietitian_plus.domain.dietitian.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DietitianResponseDto {

    @JsonProperty("dietitian_id")
    private Long dietitianId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String title;

}
