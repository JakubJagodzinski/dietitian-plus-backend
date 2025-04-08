package com.example.dietitian_plus.user;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setHeight(user.getHeight());
        userDto.setStartingWeight(user.getStartingWeight());
        userDto.setCurrentWeight(user.getCurrentWeight());
        userDto.setDietitianId(user.getDietitian().getDietitianId());
        userDto.setIsActive(user.getIsActive());

        return userDto;
    }

    public List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
