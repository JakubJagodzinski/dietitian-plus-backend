package com.example.dietitian_plus.accountsubscription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountSubscriptionRepository extends JpaRepository<AccountSubscription, UUID> {

    Optional<AccountSubscription> findByUser_UserIdAndAccountSubscriptionStatus(UUID userId, AccountSubscriptionStatus accountSubscriptionStatus);

    List<AccountSubscription> findAllByUser_UserIdAndBillingPeriodEndGreaterThanEqual(UUID userUserId, LocalDateTime billingPeriodEndIsGreaterThan);

}
