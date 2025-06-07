package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.meal.dto.CreateMealRequestDto;
import com.example.dietitian_plus.domain.meal.dto.MealDtoMapper;
import com.example.dietitian_plus.domain.meal.dto.MealResponseDto;
import com.example.dietitian_plus.domain.meal.dto.UpdateMealRequestDto;
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

    private static final String MEAL_NOT_FOUND_MESSAGE = "Meal not found";
    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    public List<MealResponseDto> getMeals() {
        return mealDtoMapper.toDtoList(mealRepository.findAll());
    }

    @Transactional
    public MealResponseDto getMealById(Long mealId) throws EntityNotFoundException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        return mealDtoMapper.toDto(meal);
    }

    @Transactional
    public List<MealResponseDto> getMealsByPatientId(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return mealDtoMapper.toDtoList(mealRepository.findByPatient_Id(patientId));
    }

    @Transactional
    public MealResponseDto createMeal(CreateMealRequestDto createMealRequestDto) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(createMealRequestDto.getPatientId()).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.findById(createMealRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Meal meal = new Meal();

        meal.setPatient(patient);
        meal.setDietitian(dietitian);
        meal.setDatetime(createMealRequestDto.getDatetime());

        return mealDtoMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public MealResponseDto updateMealById(Long mealId, UpdateMealRequestDto updateMealRequestDto) throws EntityNotFoundException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        Patient patient = patientRepository.findById(updateMealRequestDto.getPatientId()).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.findById(updateMealRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        meal.setPatient(patient);
        meal.setDietitian(dietitian);
        meal.setDatetime(updateMealRequestDto.getDatetime());

        return mealDtoMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public void deleteMealById(Long mealId) throws EntityNotFoundException {
        if (!mealRepository.existsById(mealId)) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        mealRepository.deleteById(mealId);
    }

}
