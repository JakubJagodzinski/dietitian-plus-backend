package com.example.dietitian_plus.email;

import com.example.dietitian_plus.email.contentproviders.EmailContentProvider;
import com.example.dietitian_plus.email.contentproviders.RegistrationEmailContentProvider;
import com.example.dietitian_plus.email.contentproviders.SubscriptionActivationContentProvider;
import com.example.dietitian_plus.email.contentproviders.SubscriptionCancelledContentProvider;
import com.example.dietitian_plus.user.Role;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Async
    public void sendEmail(String to, String subject, EmailContentProvider contentProvider) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(contentProvider.build(), true);

        mailSender.send(message);
    }

    public void sendRegistrationEmail(String to, String firstName, Role role, String verificationToken) throws MessagingException {
        String verificationLink = generateVerificationLink(verificationToken);
        EmailContentProvider contentProvider = new RegistrationEmailContentProvider(firstName, role, verificationLink);
        sendEmail(to, "Complete your registration process", contentProvider);
    }

    public void sendSubscriptionConfirmationEmail(String to, String firstName, LocalDateTime nextPaymentDate, Long amount, String currency) throws MessagingException {
        EmailContentProvider contentProvider = new SubscriptionActivationContentProvider(firstName, nextPaymentDate, amount, currency);
        sendEmail(to, "Account subscription activated", contentProvider);
    }

    public void sendSubscriptionCancelledEmail(String to, String firstName, LocalDate billingPeriodEnd) throws MessagingException {
        EmailContentProvider contentProvider = new SubscriptionCancelledContentProvider(firstName, billingPeriodEnd);
        sendEmail(to, "Account subscription cancelled", contentProvider);
    }

}
