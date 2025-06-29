package com.example.dietitian_plus.email.contentproviders;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class SubscriptionActivationContentProvider implements EmailContentProvider {

    private final String firstName;

    private final LocalDateTime nextPaymentDate;

    private final Long paymentAmount;

    private final String paymentCurrency;

    @Override
    public String build() {
        String formattedDate = nextPaymentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        String amountWithCurrency = (String.format("%.2f", paymentAmount / 100.0)) + " " + paymentCurrency.toUpperCase();

        return "<p>Hi " + firstName + ",</p>" +
                "<p>Thank you for your payment. Your subscription has been successfully activated!</p>" +
                "<p>Your next payment of <strong>" + amountWithCurrency + "</strong> is scheduled for <strong>" + formattedDate + "</strong>.</p>" +
                "<p>This is a monthly plan, and the same amount will be charged every month from now until you cancel your subscription.</p>" +
                "<p>You now have full access to all features of Dietitian+.</p>" +
                "<p>Best regards,<br>The Dietitian+ Team</p>";
    }

}
