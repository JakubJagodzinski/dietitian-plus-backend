package com.example.dietitian_plus.user;

import com.example.dietitian_plus.accountsubscription.AccountSubscription;
import com.example.dietitian_plus.accountsubscription.AccountSubscriptionService;
import com.example.dietitian_plus.accountsubscription.AccountSubscriptionStatus;
import com.example.dietitian_plus.common.constants.messages.PasswordMessages;
import com.example.dietitian_plus.user.dto.request.ChangePasswordRequestDto;
import com.example.dietitian_plus.user.dto.response.AccountSubscriptionStatusResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AccountSubscriptionService accountSubscriptionService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountSubscriptionStatusResponseDto getUserActiveSubscriptionStatus(Principal connectedUser) throws EntityNotFoundException {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        AccountSubscriptionStatusResponseDto accountSubscriptionStatusResponseDto = new AccountSubscriptionStatusResponseDto();

        AccountSubscription accountSubscription = accountSubscriptionService.getSubscriptionWithTimeLeft(user.getUserId());

        if (accountSubscription == null) {
            accountSubscriptionStatusResponseDto.setAccountSubscriptionStatus(AccountSubscriptionStatus.NO_SUBSCRIPTION);
            accountSubscriptionStatusResponseDto.setDaysLeft(0);
            accountSubscriptionStatusResponseDto.setBillingPeriodEnd(null);
        } else {
            LocalDateTime billingPeriodEnd = accountSubscription.getBillingPeriodEnd();

            int daysLeft = 0;
            if (billingPeriodEnd != null) {
                daysLeft = (int) Duration.between(LocalDateTime.now(), billingPeriodEnd).toDays();

                if (daysLeft < 0) {
                    daysLeft = 0;
                }
            }

            accountSubscriptionStatusResponseDto.setDaysLeft(daysLeft);
            accountSubscriptionStatusResponseDto.setAccountSubscriptionStatus(accountSubscription.getAccountSubscriptionStatus());
            accountSubscriptionStatusResponseDto.setBillingPeriodEnd(billingPeriodEnd);
        }

        return accountSubscriptionStatusResponseDto;
    }

    @Transactional
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto, Principal connectedUser) throws IllegalArgumentException {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(changePasswordRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException(PasswordMessages.WRONG_PASSWORD);
        }

        if (!changePasswordRequestDto.getNewPassword().equals(changePasswordRequestDto.getConfirmationPassword())) {
            throw new IllegalArgumentException(PasswordMessages.PASSWORDS_DONT_MATCH);
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));

        userRepository.save(user);
    }

}
