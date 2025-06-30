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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountSubscriptionService {

    private final AccountSubscriptionRepository accountSubscriptionRepository;

    private final EmailService emailService;

    public boolean hasUserSubscriptionTimeLeft(UUID userId) {
        List<AccountSubscription> accountSubscription = accountSubscriptionRepository.findAllByUser_UserIdAndBillingPeriodEndGreaterThanEqual(userId, LocalDateTime.now());

        return !accountSubscription.isEmpty();
    }

    public boolean hasUserActiveSubscription(UUID userId) {
        AccountSubscription accountSubscription = accountSubscriptionRepository.findByUser_UserIdAndAccountSubscriptionStatus(userId, AccountSubscriptionStatus.ACTIVE).orElse(null);

        return accountSubscription != null;
    }

    public AccountSubscription getSubscriptionWithTimeLeft(UUID userId) {
        List<AccountSubscription> accountSubscriptionList = accountSubscriptionRepository.findAllByUser_UserIdAndBillingPeriodEndGreaterThanEqual(userId, LocalDateTime.now());

        if (accountSubscriptionList.isEmpty()) {
            return null;
        }

        return accountSubscriptionList.getFirst();
    }

    public AccountSubscription getUserActiveSubscription(UUID userId) {
        return accountSubscriptionRepository.findByUser_UserIdAndAccountSubscriptionStatus(userId, AccountSubscriptionStatus.ACTIVE).orElse(null);
    }

    @Transactional
    public void cancelSubscription(User user) throws IllegalStateException, MessagingException {
        try {
            AccountSubscription accountSubscription = getUserActiveSubscription(user.getUserId());

            if (accountSubscription == null) {
                throw new IllegalStateException(AccountSubscriptionMessages.NO_ACTIVE_SUBSCRIPTION_ON_THIS_ACCOUNT);
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
        AccountSubscription accountSubscription = getUserActiveSubscription(user.getUserId());

        if (accountSubscription == null) {
            accountSubscription = new AccountSubscription();

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
