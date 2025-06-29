package com.example.dietitian_plus.verification;

import com.example.dietitian_plus.common.constants.messages.UserMessages;
import com.example.dietitian_plus.common.constants.messages.VerificationEmailMessages;
import com.example.dietitian_plus.common.constants.messages.VerificationTokenMessages;
import com.example.dietitian_plus.email.EmailService;
import com.example.dietitian_plus.user.User;
import com.example.dietitian_plus.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    private final EmailService emailService;

    private void markExistingTokensAsUsed(User user) {
        List<VerificationToken> tokens = verificationTokenRepository.findAllByUser_UserIdAndUsedFalse(user.getUserId());

        for (VerificationToken token : tokens) {
            token.setUsed(true);
        }

        verificationTokenRepository.saveAll(tokens);
    }

    @Transactional
    public String createVerificationToken(User user) {
        markExistingTokensAsUsed(user);

        VerificationToken token = new VerificationToken();

        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(24));

        VerificationToken savedToken = verificationTokenRepository.save(token);

        return savedToken.getToken();
    }

    @Transactional
    public void verifyUser(String token) throws EntityNotFoundException, IllegalArgumentException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElse(null);

        if (verificationToken == null) {
            throw new EntityNotFoundException(VerificationTokenMessages.VERIFICATION_TOKEN_NOT_FOUND);
        }

        if (verificationToken.isUsed()) {
            throw new IllegalArgumentException(VerificationTokenMessages.VERIFICATION_TOKEN_ALREADY_USED);
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED);
        }

        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);

        verificationToken.setUsed(true);
        verificationTokenRepository.save(verificationToken);
    }

    @Transactional
    public void resendVerificationEmail(String email) throws EntityNotFoundException, MailException {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException(UserMessages.USER_NOT_FOUND);
        }

        if (user.isVerified()) {
            throw new IllegalArgumentException(UserMessages.ACCOUNT_ALREADY_VERIFIED);
        }

        String newToken = createVerificationToken(user);

        try {
            emailService.sendRegistrationEmail(user.getEmail(), user.getFirstName(), user.getRole(), newToken);
        } catch (Exception e) {
            throw new MailSendException(VerificationEmailMessages.FAILED_TO_SEND_VERIFICATION_EMAIL, e);
        }
    }

}
