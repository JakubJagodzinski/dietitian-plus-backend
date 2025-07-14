package com.example.dietitian_plus.accountsubscription;

import com.example.dietitian_plus.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account_subscriptions")
public class AccountSubscription {

    @Id
    @GeneratedValue
    @Column(name = "account_subscription_id", updatable = false, nullable = false)
    private UUID accountSubscriptionId;

    @Column(name = "stripe_subscription_id", unique = true, nullable = false)
    private String stripeSubscriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_account_subscriptions_user"))
    private User user;

    @Column(name = "billing_period_end")
    private LocalDateTime billingPeriodEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_subscription_status", nullable = false)
    private AccountSubscriptionStatus accountSubscriptionStatus = AccountSubscriptionStatus.ACTIVE;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
