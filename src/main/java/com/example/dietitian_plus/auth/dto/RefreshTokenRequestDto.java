package com.example.dietitian_plus.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshTokenRequestDto {

    @JsonProperty("refresh_token")
    private String refreshToken;

}
