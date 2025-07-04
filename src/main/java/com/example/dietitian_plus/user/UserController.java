package com.example.dietitian_plus.user;

import com.example.dietitian_plus.common.constants.messages.PasswordMessages;
import com.example.dietitian_plus.common.dto.ApiErrorResponseDto;
import com.example.dietitian_plus.common.dto.MessageResponseDto;
import com.example.dietitian_plus.user.dto.request.ChangePasswordRequestDto;
import com.example.dietitian_plus.user.dto.response.AccountSubscriptionStatusResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Change password (only when logged in)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Password changed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed to change password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            )
    })
    @PatchMapping("/users/change-password")
    public ResponseEntity<MessageResponseDto> changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto, Principal connectedUser) {
        userService.changePassword(changePasswordRequestDto, connectedUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(PasswordMessages.PASSWORD_CHANGED_SUCCESSFULLY));
    }

    @Operation(
            summary = "Get user active subscription status"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Detailed info about user active subscription",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AccountSubscriptionStatusResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            )
    })
    @GetMapping("/users/subscriptions/status")
    public ResponseEntity<AccountSubscriptionStatusResponseDto> getUserActiveSubscriptionStatus(Principal connectedUser) {
        AccountSubscriptionStatusResponseDto accountSubscriptionStatusResponseDto = userService.getUserActiveSubscriptionStatus(connectedUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountSubscriptionStatusResponseDto);
    }

}
