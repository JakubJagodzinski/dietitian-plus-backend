package com.example.dietitian_plus.domain.dietitian.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"dietitian_id", "first_name", "last_name", "title"})
public class DietitianResponseDto {

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("title")
    private String title;

}
