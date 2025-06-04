package com.example.dietitian_plus.domain.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDto {

    private Long patientId;

    private String email;

    private String firstName;

    private String lastName;

    private Float height;

    private Float startingWeight;

    private Float currentWeight;

    private Boolean isActive;

    private Long dietitianId;

}
