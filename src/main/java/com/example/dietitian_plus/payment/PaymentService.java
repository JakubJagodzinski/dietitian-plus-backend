package com.example.dietitian_plus.payment;

import com.example.dietitian_plus.accountsubscription.AccountSubscriptionService;
import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.AccountSubscriptionMessages;
import com.example.dietitian_plus.common.constants.messages.PaymentMessages;
import com.example.dietitian_plus.config.StripeConfig;
import com.example.dietitian_plus.payment.dto.ActivateSubscriptionResponseDto;
import com.example.dietitian_plus.user.User;
import com.example.dietitian_plus.user.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    private final AccountSubscriptionService accountSubscriptionService;

    private final StripeConfig stripeConfig;

    private final SecurityUtils securityUtils;

    private static final String userIdMetadata = "userId";

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeConfig.getApiKey();
    }

    public ActivateSubscriptionResponseDto activateSubscription(User user) throws StripeException, IllegalArgumentException {
        if (accountSubscriptionService.hasUserActiveSubscription(user.getUserId())) {
            throw new IllegalArgumentException(AccountSubscriptionMessages.ACCOUNT_ALREADY_HAS_AN_ACTIVE_SUBSCRIPTION);
        }

        if (securityUtils.isAdmin()) {
            throw new IllegalArgumentException(AccountSubscriptionMessages.YOU_CANNOT_ACTIVATE_SUBSCRIPTION_FOR_ADMIN_ACCOUNT);
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("http://localhost:8090/api/v1/payments/success")
                .setCancelUrl("http://localhost:8090/api/v1/payments/cancelled")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(stripeConfig.getPriceId())
                                .setQuantity(1L)
                                .build()
                )
                .putMetadata(userIdMetadata, user.getUserId().toString())
                .build();

        Session session = Session.create(params);

        ActivateSubscriptionResponseDto activateSubscriptionResponseDto = new ActivateSubscriptionResponseDto();

        activateSubscriptionResponseDto.setCheckoutUrl(session.getUrl());

        return activateSubscriptionResponseDto;
    }

    @Transactional
    public void handleStripeWebhook(String payload, String sigHeader) throws Exception {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, stripeConfig.getWebhookSecret());
        } catch (SignatureVerificationException e) {
            throw new Exception(PaymentMessages.PAYMENT_WEBHOOK_INVALID_SIGNATURE, e);
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);

            if (session == null) {
                return;
            }

            UUID userId = UUID.fromString(session.getMetadata().get(userIdMetadata));
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return;
            }

            registerSuccessfulPayment(user, session);

            LocalDateTime billingPeriodEnd = accountSubscriptionService.extendSubscription(user, session);

            accountSubscriptionService.sendSubscriptionActivationEmail(user, session, billingPeriodEnd);
        }
    }

    private void registerSuccessfulPayment(User user, Session session) {
        String externalPaymentId = session.getSubscription();

        if (paymentRepository.existsByExternalPaymentId(externalPaymentId)) {
            return;
        }

        Payment payment = new Payment();

        payment.setUser(user);

        payment.setAmountInCents(session.getAmountTotal());
        payment.setCurrency(session.getCurrency());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setExternalPaymentId(session.getSubscription());
        payment.setPaidAt(LocalDateTime.now());

        paymentRepository.save(payment);
    }

}
