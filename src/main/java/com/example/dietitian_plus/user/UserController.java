package com.example.dietitian_plus.user;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.common.constants.Messages;
import com.example.dietitian_plus.user.dto.ChangePasswordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("change-password")
    public ResponseEntity<MessageResponseDto> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto, Principal connectedUser) {
        userService.changePassword(changePasswordRequestDto, connectedUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(Messages.PASSWORD_CHANGED_SUCCESSFULLY));
    }

}
