package com.example.dietitian_plus.meal;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
import com.example.dietitian_plus.user.User;
import com.example.dietitian_plus.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final DietitianRepository dietitianRepository;

    private final MealMapper mealMapper;

    private final String MEAL_NOT_FOUND_MESSAGE = "Meal not found";
    private final String USER_NOT_FOUND_MESSAGE = "User not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public MealService(MealRepository mealRepository, UserRepository userRepository, DietitianRepository dietitianRepository, MealMapper mealMapper) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.dietitianRepository = dietitianRepository;
        this.mealMapper = mealMapper;
    }

    public List<MealDto> getMeals() {
        List<Meal> meals = mealRepository.findAll();
        List<MealDto> mealsDto = new ArrayList<>();

        for (Meal meal : meals) {
            mealsDto.add(mealMapper.toDto(meal));
        }

        return mealsDto;
    }

    public MealDto getMealById(Long id) throws EntityNotFoundException {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        return mealMapper.toDto(mealRepository.getReferenceById(id));
    }

    @Transactional
    public MealDto createMeal(CreateMealDto createMealDto) throws EntityNotFoundException {
        if (!userRepository.existsById(createMealDto.getUserId())) {
            throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        if (!dietitianRepository.existsById(createMealDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Meal meal = new Meal();

        User user = userRepository.getReferenceById(createMealDto.getUserId());
        meal.setUser(user);

        Dietitian dietitian = dietitianRepository.getReferenceById(createMealDto.getDietitianId());
        meal.setDietitian(dietitian);

        return mealMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public MealDto updateMealById(Long id, CreateMealDto updateMealDto) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        Meal meal = mealRepository.getReferenceById(id);

        meal.setDatetime(updateMealDto.getDatetime());

        return mealMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public void deleteMealById(Long id) throws EntityNotFoundException {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        mealRepository.deleteById(id);
    }

}
