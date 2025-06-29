package com.example.dietitian_plus.email;

import com.example.dietitian_plus.common.constants.messages.UserMessages;
import com.example.dietitian_plus.user.Role;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    private String generateVerificationLink(String verificationToken) {
        String protocol = serverAddress.contains("localhost") ? "http" : "https";

        return protocol + "://" + serverAddress + ":" + serverPort + "/api/v1/auth/verify?token=" + verificationToken;
    }

    private String buildEmailBody(String firstName, Role role, String verificationLink) {
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

    public void sendRegistrationEmail(String to, String firstName, Role role, String verificationToken) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);

        helper.setSubject("Complete your registration process");

        String body = buildEmailBody(firstName, role, generateVerificationLink(verificationToken));
        helper.setText(body, true);

        mailSender.send(message);
    }

}
