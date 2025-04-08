package com.example.dietitian_plus.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;

    private String email;

    private String firstName;

    private String lastName;

    private Float height;

    private Float startingWeight;

    private Float currentWeight;

    private Boolean isActive;

    private Long dietitianId;

}
