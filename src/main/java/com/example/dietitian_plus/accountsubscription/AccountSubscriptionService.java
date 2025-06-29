package com.example.dietitian_plus.accountsubscription;

import com.example.dietitian_plus.common.constants.messages.AccountSubscriptionMessages;
import com.example.dietitian_plus.common.constants.messages.EmailMessages;
import com.example.dietitian_plus.email.EmailService;
import com.example.dietitian_plus.user.User;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountSubscriptionService {

    private final AccountSubscriptionRepository accountSubscriptionRepository;

    private final EmailService emailService;

    @Transactional
    public void cancelSubscription(User user) throws IllegalStateException, MessagingException {
        try {
            AccountSubscription accountSubscription = accountSubscriptionRepository.findByUser_UserIdAndAccountSubscriptionStatus(user.getUserId(), AccountSubscriptionStatus.ACTIVE).orElse(null);

            if (accountSubscription == null) {
                throw new IllegalStateException(AccountSubscriptionMessages.NO_SUBSCRIPTION_ACTIVE_ON_THIS_ACCOUNT);
            }

            Subscription stripeSubscription = Subscription.retrieve(accountSubscription.getStripeSubscriptionId());

            Map<String, Object> params = new HashMap<>();
            params.put("cancel_at_period_end", true);

            stripeSubscription.update(params);

            accountSubscription.setAccountSubscriptionStatus(AccountSubscriptionStatus.CANCELLED);
            accountSubscription.setUpdatedAt(LocalDateTime.now());
            accountSubscriptionRepository.save(accountSubscription);

            try {
                emailService.sendSubscriptionCancelledEmail(user.getEmail(), user.getFirstName(), LocalDate.from(accountSubscription.getBillingPeriodEnd()));
            } catch (MessagingException e) {
                throw new MessagingException(EmailMessages.FAILED_TO_SEND_EMAIL);
            }
        } catch (StripeException e) {
            throw new RuntimeException(AccountSubscriptionMessages.FAILED_TO_CANCEL_SUBSCRIPTION);
        }
    }

    @Transactional
    public LocalDateTime extendSubscription(User user, Session session) {
        AccountSubscription accountSubscription = accountSubscriptionRepository.findByUser_UserIdAndAccountSubscriptionStatus(user.getUserId(), AccountSubscriptionStatus.ACTIVE).orElse(null);

        if (accountSubscription == null) {
            accountSubscription = new AccountSubscription();

            accountSubscription.setAccountSubscriptionStatus(AccountSubscriptionStatus.ACTIVE);
            accountSubscription.setUser(user);
            try {
                Subscription stripeSubscription = Subscription.retrieve(session.getSubscription());
                accountSubscription.setStripeSubscriptionId(stripeSubscription.getId());
            } catch (Exception e) {
                throw new RuntimeException(AccountSubscriptionMessages.FAILED_TO_EXTEND_SUBSCRIPTION);
            }
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime currentEnd = accountSubscription.getBillingPeriodEnd();

        LocalDateTime newEnd = (currentEnd != null && currentEnd.isAfter(now))
                ? currentEnd.plusMonths(1)
                : now.plusMonths(1);

        accountSubscription.setBillingPeriodEnd(newEnd);

        accountSubscriptionRepository.save(accountSubscription);

        return accountSubscription.getBillingPeriodEnd();
    }

    public void sendSubscriptionActivationEmail(User user, Session session, LocalDateTime billingPeriodEnd) throws MailSendException {
        try {
            emailService.sendSubscriptionConfirmationEmail(
                    user.getEmail(),
                    user.getFirstName(),
                    billingPeriodEnd,
                    session.getAmountTotal(),
                    session.getCurrency()
            );
        } catch (Exception e) {
            throw new MailSendException(EmailMessages.FAILED_TO_SEND_SUBSCRIPTION_ACTIVATION_EMAIL);
        }
    }

}
