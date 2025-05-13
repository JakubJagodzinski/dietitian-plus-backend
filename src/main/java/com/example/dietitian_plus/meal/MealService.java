package com.example.dietitian_plus.meal;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
import com.example.dietitian_plus.dish.dto.DishResponseDto;
import com.example.dietitian_plus.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.meal.dto.CreateMealRequestDto;
import com.example.dietitian_plus.meal.dto.MealResponseDto;
import com.example.dietitian_plus.meal.dto.MealDtoMapper;
import com.example.dietitian_plus.patient.Patient;
import com.example.dietitian_plus.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final MealDtoMapper mealDtoMapper;
    private final DishDtoMapper dishDtoMapper;

    private final String MEAL_NOT_FOUND_MESSAGE = "Meal not found";
    private final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public MealService(MealRepository mealRepository, PatientRepository patientRepository, DietitianRepository dietitianRepository, MealDtoMapper mealDtoMapper, DishDtoMapper dishDtoMapper) {
        this.mealRepository = mealRepository;
        this.patientRepository = patientRepository;
        this.dietitianRepository = dietitianRepository;
        this.mealDtoMapper = mealDtoMapper;
        this.dishDtoMapper = dishDtoMapper;
    }

    public List<MealResponseDto> getMeals() {
        List<Meal> meals = mealRepository.findAll();
        List<MealResponseDto> mealsDto = new ArrayList<>();

        for (Meal meal : meals) {
            mealsDto.add(mealDtoMapper.toDto(meal));
        }

        return mealsDto;
    }

    public List<DishResponseDto> getMealDishes(Long id) throws EntityNotFoundException {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        Meal meal = mealRepository.getReferenceById(id);

        return dishDtoMapper.toDtoList(meal.getDishes());
    }

    public MealResponseDto getMealById(Long id) throws EntityNotFoundException {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        return mealDtoMapper.toDto(mealRepository.getReferenceById(id));
    }

    @Transactional
    public MealResponseDto createMeal(CreateMealRequestDto createMealRequestDto) throws EntityNotFoundException {
        if (!patientRepository.existsById(createMealRequestDto.getPatientId())) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!dietitianRepository.existsById(createMealRequestDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Meal meal = new Meal();

        Patient patient = patientRepository.getReferenceById(createMealRequestDto.getPatientId());
        meal.setPatient(patient);

        Dietitian dietitian = dietitianRepository.getReferenceById(createMealRequestDto.getDietitianId());
        meal.setDietitian(dietitian);

        return mealDtoMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public MealResponseDto updateMealById(Long id, CreateMealRequestDto updateMealDto) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Meal meal = mealRepository.getReferenceById(id);

        meal.setDatetime(updateMealDto.getDatetime());

        return mealDtoMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public void deleteMealById(Long id) throws EntityNotFoundException {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        mealRepository.deleteById(id);
    }

}
