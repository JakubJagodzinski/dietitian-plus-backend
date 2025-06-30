package com.example.dietitian_plus.paywall;

import com.example.dietitian_plus.accountsubscription.AccountSubscriptionService;
import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaywallService {

    private final AccountSubscriptionService accountSubscriptionService;

    private final SecurityUtils securityUtils;

    public boolean hasActiveSubscription() {
        User currentUser = securityUtils.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return accountSubscriptionService.hasUserSubscriptionTimeLeft(currentUser.getUserId());
    }

}
