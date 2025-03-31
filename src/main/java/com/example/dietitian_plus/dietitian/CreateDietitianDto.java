package com.example.dietitian_plus.dietitian;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateDietitianDto {

    private String email;

    private String password;

    private String title;

    private String firstName;

    private String lastName;

}
