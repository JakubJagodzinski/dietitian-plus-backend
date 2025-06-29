package com.example.dietitian_plus.accountsubscription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountSubscriptionRepository extends JpaRepository<AccountSubscription, UUID> {

    Optional<AccountSubscription> findByUser_UserIdAndAccountSubscriptionStatus(UUID userId, AccountSubscriptionStatus accountSubscriptionStatus);

}
