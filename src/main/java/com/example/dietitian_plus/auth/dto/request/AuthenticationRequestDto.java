package com.example.dietitian_plus.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"email", "password"})
public class AuthenticationRequestDto {

    @Schema(
            description = "User's email address used for login",
            example = "user@example.com"
    )
    @Email(message = "Invalid email format")
    @NotBlank
    @JsonProperty("email")
    private String email;

    @Schema(
            description = "User's password",
            example = "P@ssw0rd123"
    )
    @NotBlank
    @JsonProperty("password")
    private String password;

}
