package com.example.dietitian_plus.user;

import com.example.dietitian_plus.common.constants.messages.PasswordMessages;
import com.example.dietitian_plus.user.dto.ChangePasswordRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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
