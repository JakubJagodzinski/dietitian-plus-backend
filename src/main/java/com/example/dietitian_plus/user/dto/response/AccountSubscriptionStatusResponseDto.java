package com.example.dietitian_plus.user.dto.response;

import com.example.dietitian_plus.accountsubscription.AccountSubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"account_subscription_status", "days_left", "billing_period_end"})
public class AccountSubscriptionStatusResponseDto {

    @JsonProperty("account_subscription_status")
    private AccountSubscriptionStatus accountSubscriptionStatus;

    @JsonProperty("days_left")
    private Integer daysLeft;

    @JsonProperty("billing_period_end")
    private LocalDateTime billingPeriodEnd;

}
