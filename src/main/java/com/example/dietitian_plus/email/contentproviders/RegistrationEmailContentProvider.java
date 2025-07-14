package com.example.dietitian_plus.email.contentproviders;

import com.example.dietitian_plus.common.constants.messages.UserMessages;
import com.example.dietitian_plus.user.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationEmailContentProvider implements EmailContentProvider {

    private final String firstName;

    private final Role role;

    private final String verificationLink;

    @Override
    public String build() {
        String intro = switch (role) {
            case DIETITIAN -> "Thank you for registering as a dietitian on our platform. " +
                    "Please verify your email to complete your account setup and start helping patients manage their health.";
            case PATIENT -> "Thank you for registering as a patient on our platform. " +
                    "Please verify your email to complete your account and start working with a dietitian.";
            default -> throw new IllegalArgumentException(UserMessages.INVALID_USER_ROLE);
        };

        return "<p>Hi " + firstName + ",</p>" +
                "<p>" + intro + "</p>" +
                "<p>Click <a href=\"" + verificationLink + "\">here</a> to verify your account.</p>" +
                "<p>Best regards,<br>The Dietitian+ Team</p>";
    }

}
