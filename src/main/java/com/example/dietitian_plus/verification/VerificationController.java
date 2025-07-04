package com.example.dietitian_plus.verification;

import com.example.dietitian_plus.common.constants.messages.EmailMessages;
import com.example.dietitian_plus.common.constants.messages.UserMessages;
import com.example.dietitian_plus.common.dto.MessageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationTokenService verificationTokenService;

    @Operation(
            summary = "Verify account",
            parameters = {
                    @Parameter(
                            name = "token",
                            description = "Verification token",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", example = "ce47e175-1a3f-43ae-9e78-1d83c18c1a12")
                    ),
            }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account verified successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed to authorize account",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDto.class)
                    )
            )
    })
    @GetMapping("/verify")
    public ResponseEntity<MessageResponseDto> verifyUser(@RequestParam String token) {
        verificationTokenService.verifyUser(token);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(UserMessages.ACCOUNT_VERIFIED_SUCCESSFULLY));
    }

    @Operation(
            summary = "Resend verification email",
            parameters = {
                    @Parameter(
                            name = "email",
                            description = "User email",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", example = "john.doe@dietitian_plus.com")
                    ),
            }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Verification email resend",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Account already verified",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDto.class)
                    )
            )
    })
    @PostMapping("/resend-verification")
    public ResponseEntity<MessageResponseDto> resendVerificationEmail(@RequestParam String email) {
        verificationTokenService.resendVerificationEmail(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(EmailMessages.VERIFICATION_EMAIL_RESEND));
    }

}
