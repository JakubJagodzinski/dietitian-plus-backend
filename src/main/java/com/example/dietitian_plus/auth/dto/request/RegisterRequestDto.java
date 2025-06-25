package com.example.dietitian_plus.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegisterRequestDto {

    @Schema(
            description = "User's first name",
            example = "John"
    )
    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Doe"
    )
    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @Schema(
            description = "User's email address",
            example = "john.doe@example.com"
    )
    @Email(message = "Invalid email format")
    @NotBlank
    private String email;

    @Schema(
            description = "User's password",
            example = "P@ssw0rd123"
    )
    @NotBlank
    private String password;

    @Schema(
            description = "User's role in the system",
            example = "DIETITIAN",
            defaultValue = "PATIENT"
    )
    @NotBlank
    private String role;

    @Schema(
            description = "User's phone number",
            example = "+48 123 456 789",
            defaultValue = "+48 123 456 789"
    )
    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;

}
