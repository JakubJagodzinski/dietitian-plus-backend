package com.example.dietitian_plus.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Float height;

    private Float startingWeight;

    private Long dietitianId;

}
