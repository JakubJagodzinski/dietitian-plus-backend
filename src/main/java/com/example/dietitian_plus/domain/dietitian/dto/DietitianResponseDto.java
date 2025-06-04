package com.example.dietitian_plus.domain.dietitian.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DietitianResponseDto {

    private Long dietitianId;

    private String email;

    private String password;

    private String title;

    private String firstName;

    private String lastName;

}
