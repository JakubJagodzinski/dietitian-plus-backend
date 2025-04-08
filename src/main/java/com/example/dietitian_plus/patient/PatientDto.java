package com.example.dietitian_plus.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

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
