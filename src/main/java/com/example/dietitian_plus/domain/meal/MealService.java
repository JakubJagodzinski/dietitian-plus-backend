package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.meal.dto.CreateMealRequestDto;
import com.example.dietitian_plus.domain.meal.dto.MealDtoMapper;
import com.example.dietitian_plus.domain.meal.dto.MealResponseDto;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final MealDtoMapper mealDtoMapper;
    private final DishDtoMapper dishDtoMapper;

    private static final String MEAL_NOT_FOUND_MESSAGE = "Meal not found";
    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    public List<MealResponseDto> getMeals() {
        return mealDtoMapper.toDtoList(mealRepository.findAll());
    }

    @Transactional
    public List<DishResponseDto> getMealDishes(Long id) throws EntityNotFoundException {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        Meal meal = mealRepository.getReferenceById(id);

        return dishDtoMapper.toDtoList(meal.getDishes());
    }

    @Transactional
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
