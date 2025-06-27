package com.example.dietitian_plus.auth;

import com.example.dietitian_plus.auth.dto.request.AuthenticationRequestDto;
import com.example.dietitian_plus.auth.dto.request.RefreshTokenRequestDto;
import com.example.dietitian_plus.auth.dto.request.RegisterRequestDto;
import com.example.dietitian_plus.auth.dto.response.AuthenticationResponseDto;
import com.example.dietitian_plus.auth.dto.response.RefreshTokenResponseDto;
import com.example.dietitian_plus.common.dto.MessageResponseDto;
import com.example.dietitian_plus.common.constants.messages.UserMessages;
import com.example.dietitian_plus.common.dto.ApiErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(summary = "Register new account")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email is already taken",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        service.register(registerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(UserMessages.USER_CREATED_SUCCESSFULLY));
    }

    @Operation(summary = "Authenticate with email and password")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Wrong username or password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        AuthenticationResponseDto authenticationResponseDto = service.authenticate(authenticationRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authenticationResponseDto);
    }

    @Operation(summary = "Use refresh token to generate new access and refresh token")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Token refreshed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RefreshTokenResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Provided refresh token is invalid or expired",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshTokenResponseDto refreshTokenResponseDto = service.refreshToken(refreshTokenRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(refreshTokenResponseDto);
    }

}
