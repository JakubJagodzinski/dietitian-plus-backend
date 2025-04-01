package com.example.dietitian_plus.dietitian;

import com.example.dietitian_plus.dish.DishDto;
import com.example.dietitian_plus.dish.DishMapper;
import com.example.dietitian_plus.dish.DishRepository;
import com.example.dietitian_plus.user.UserDto;
import com.example.dietitian_plus.user.UserMapper;
import com.example.dietitian_plus.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DietitianService {

    private final DietitianRepository dietitianRepository;
    private final UserRepository userRepository;

    private final DietitianMapper dietitianMapper;
    private final UserMapper userMapper;
    private final DishMapper dishMapper;

    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";
    private final DishRepository dishRepository;

    @Autowired
    public DietitianService(DietitianRepository dietitianRepository, UserRepository userRepository, DietitianMapper dietitianMapper, UserMapper userMapper, DishMapper dishMapper, DishRepository dishRepository) {
        this.dietitianRepository = dietitianRepository;
        this.userRepository = userRepository;
        this.dietitianMapper = dietitianMapper;
        this.userMapper = userMapper;
        this.dishMapper = dishMapper;
        this.dishRepository = dishRepository;
    }

    public List<DietitianDto> getDietitians() {
        List<Dietitian> dietitians = dietitianRepository.findAll();
        List<DietitianDto> dietitiansDto = new ArrayList<>();

        for (var dietitian : dietitians) {
            dietitiansDto.add(dietitianMapper.toDto(dietitian));
        }

        return dietitiansDto;
    }

    public DietitianDto getDietitianById(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        return dietitianMapper.toDto(dietitianRepository.getReferenceById(id));
    }

    public List<UserDto> getDietitianUsers(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        return userMapper.toDtoList(userRepository.findByDietitian(dietitianRepository.getReferenceById(id)));
    }

    public List<DishDto> getDietitianDishes(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        return dishMapper.toDtoList(dishRepository.findByDietitian(dietitianRepository.getReferenceById(id)));
    }

    @Transactional
    public DietitianDto createDietitian(CreateDietitianDto createDietitianDto) {
        Dietitian dietitian = new Dietitian();

        dietitian.setEmail(createDietitianDto.getEmail());
        dietitian.setTitle(createDietitianDto.getTitle());
        dietitian.setPassword(createDietitianDto.getPassword());
        dietitian.setFirstName(createDietitianDto.getFirstName());
        dietitian.setLastName(createDietitianDto.getLastName());

        return dietitianMapper.toDto(dietitianRepository.save(dietitian));
    }

    @Transactional
    public DietitianDto updateDietitianById(Long id, DietitianDto dietitianDto) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.getReferenceById(id);

        if (dietitianDto.getEmail() != null) {
            dietitian.setEmail(dietitianDto.getEmail());
        }

        if (dietitianDto.getTitle() != null) {
            dietitian.setTitle(dietitianDto.getTitle());
        }

        if (dietitianDto.getPassword() != null) {
            dietitian.setPassword(dietitianDto.getPassword());
        }

        if (dietitianDto.getFirstName() != null) {
            dietitian.setFirstName(dietitianDto.getFirstName());
        }

        if (dietitianDto.getLastName() != null) {
            dietitian.setLastName(dietitianDto.getLastName());
        }

        return dietitianMapper.toDto(dietitianRepository.save(dietitian));
    }

    @Transactional
    public void deleteDietitianById(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DIETITIAN_NOT_FOUND_MESSAGE);
        }

        dietitianRepository.deleteById(id);
    }

}
