package com.example.dietitian_plus.user;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DietitianRepository dietitianRepository;

    private final String USER_NOT_FOUND_MESSAGE = "User not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public UserService(UserRepository userRepository, DietitianRepository dietitianRepository) {
        this.userRepository = userRepository;
        this.dietitianRepository = dietitianRepository;
    }

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();

        for (User user : users) {
            usersDto.add(mapToDto(user));
        }

        return usersDto;
    }

    public UserDto getUserById(Long id) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        return mapToDto(userRepository.getReferenceById(id));
    }

    @Transactional
    public UserDto createUser(CreateUserDto createUserDto) throws EntityNotFoundException {
        User user = new User();

        if (!dietitianRepository.existsById(createUserDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.getReferenceById(createUserDto.getDietitianId());

        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setHeight(createUserDto.getHeight());
        user.setStartingWeight(createUserDto.getStartingWeight());
        user.setCurrentWeight(createUserDto.getStartingWeight());
        user.setIsActive(Boolean.TRUE);
        user.setDietitian(dietitian);

        return mapToDto(userRepository.save(user));
    }

    @Transactional
    public UserDto updateUserById(Long id, UserDto userDto) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        User user = userRepository.getReferenceById(id);

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }

        if (userDto.getHeight() != null) {
            user.setHeight(userDto.getHeight());
        }

        if (userDto.getStartingWeight() != null) {
            user.setStartingWeight(userDto.getStartingWeight());
        }

        if (userDto.getCurrentWeight() != null) {
            user.setCurrentWeight(userDto.getCurrentWeight());
        }

        if (userDto.getIsActive() != null) {
            user.setIsActive(userDto.getIsActive());
        }

        if (userDto.getDietitianId() != null) {
            if (!dietitianRepository.existsById(userDto.getDietitianId())) {
                throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
            }

            Dietitian dietitian = dietitianRepository.getReferenceById(userDto.getDietitianId());
            user.setDietitian(dietitian);
        }

        return mapToDto(userRepository.save(user));
    }

    @Transactional
    public void deleteUserById(Long id) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        userRepository.deleteById(id);
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setHeight(user.getHeight());
        userDto.setStartingWeight(user.getStartingWeight());
        userDto.setCurrentWeight(user.getCurrentWeight());
        userDto.setIsActive(user.getIsActive());
        userDto.setDietitianId(user.getDietitian().getDietitianId());

        return userDto;
    }

}
