package com.example.dietitian_plus.accountsubscription;

import com.example.dietitian_plus.common.constants.messages.AccountSubscriptionMessages;
import com.example.dietitian_plus.common.dto.ApiErrorResponseDto;
import com.example.dietitian_plus.common.dto.MessageResponseDto;
import com.example.dietitian_plus.user.User;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountSubscriptionController {

    private final AccountSubscriptionService accountSubscriptionService;

    @Schema(
            description = "Cancel subscription"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Subscription cancelled successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "There is no active subscription assigned to this account",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/subscriptions/cancel")
    public ResponseEntity<MessageResponseDto> cancelSubscription(@AuthenticationPrincipal User user) throws MessagingException {
        accountSubscriptionService.cancelSubscription(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(AccountSubscriptionMessages.SUBSCRIPTION_CANCELLED_SUCCESSFULLY));
    }

}
