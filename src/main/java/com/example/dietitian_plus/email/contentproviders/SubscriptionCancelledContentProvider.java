package com.example.dietitian_plus.email.contentproviders;


import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class SubscriptionCancelledContentProvider implements EmailContentProvider {

    private final String firstName;

    private final LocalDate billingPeriodEnd;

    @Override
    public String build() {
        String formattedDate = billingPeriodEnd.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));

        return "<p>Hi " + firstName + ",</p>" +
                "<p>Your subscription has been successfully cancelled.</p>" +
                "<p>You will still have access to Dietitian+ until <strong>" + formattedDate + "</strong>, the end of your current billing period.</p>" +
                "<p>If you change your mind, you can reactivate your subscription at any time from your account settings.</p>" +
                "<p>Best regards,<br>The Dietitian+ Team</p>";
    }

}
