package com.example.dietitian_plus.seeder.table;

import com.example.dietitian_plus.seeder.DataSeeder;
import com.example.dietitian_plus.user.Role;
import com.example.dietitian_plus.user.User;
import com.example.dietitian_plus.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements DataSeeder {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final static String ADMIN_FIRST_NAME = "admin";
    private final static String ADMIN_LAST_NAME = "admin";
    private final static String ADMIN_EMAIL = "admin@dietitian_plus.com";
    private final static String ADMIN_PASSWORD = "admin";

    @Override
    public void seed() {
        if (!userRepository.existsByEmail(ADMIN_EMAIL)) {
            User admin = new User();

            admin.setEmail(ADMIN_EMAIL);
            admin.setFirstName(ADMIN_FIRST_NAME);
            admin.setLastName(ADMIN_LAST_NAME);
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            admin.setRole(Role.ADMIN);
            admin.setVerified(true);
            admin.setSubscriptionPaid(true);

            userRepository.save(admin);
        }
    }

}
