package com.example.dietitian_plus.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePatientDto {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Float height;

    private Float startingWeight;

    private Long dietitianId;

}
