package com.example.dietitian_plus.domain.patient.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AssignDietitianToPatientRequestDto {

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

}
