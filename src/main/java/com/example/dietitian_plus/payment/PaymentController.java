package com.example.dietitian_plus.payment;

import com.example.dietitian_plus.common.constants.messages.PaymentMessages;
import com.example.dietitian_plus.common.dto.ApiErrorResponseDto;
import com.example.dietitian_plus.common.dto.MessageResponseDto;
import com.example.dietitian_plus.payment.dto.ActivateSubscriptionResponseDto;
import com.example.dietitian_plus.user.User;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Schema(
            description = "Activate Dietitian Plus Monthly Subscription for account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Subscription activated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "There is already active subscription on this account",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/subscriptions/activate")
    public ResponseEntity<ActivateSubscriptionResponseDto> activateSubscription(@AuthenticationPrincipal User user) throws StripeException {
        ActivateSubscriptionResponseDto activateSubscriptionResponseDto = paymentService.activateSubscription(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(activateSubscriptionResponseDto);
    }

    @PostMapping("/subscriptions/webhook")
    public ResponseEntity<MessageResponseDto> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) throws Exception {
        paymentService.handleStripeWebhook(payload, sigHeader);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(PaymentMessages.PAYMENT_WEBHOOK_RECEIVED));
    }

    @GetMapping("/payments/success")
    public ResponseEntity<MessageResponseDto> success() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(PaymentMessages.PAYMENT_SUCCESSFUL));
    }

    @GetMapping("/payments/cancelled")
    public ResponseEntity<MessageResponseDto> cancelled() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(PaymentMessages.PAYMENT_CANCELLED));
    }

}
