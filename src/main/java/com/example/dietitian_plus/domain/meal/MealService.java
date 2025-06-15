package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.auth.access.manager.MealAccessManager;
import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import com.example.dietitian_plus.common.constants.messages.MealMessages;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final MealDtoMapper mealDtoMapper;

    private final MealAccessManager mealAccessManager;

    public List<MealResponseDto> getAllMeals() {
        return mealDtoMapper.toDtoList(mealRepository.findAll());
    }

    @Transactional
    public MealResponseDto getMealById(Long mealId) throws EntityNotFoundException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(MealMessages.MEAL_NOT_FOUND);
        }

        mealAccessManager.checkCanAccessMeal(meal);

        return mealDtoMapper.toDto(meal);
    }

    @Transactional
    public List<MealResponseDto> getPatientAllMeals(UUID patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        mealAccessManager.checkCanGetPatientAllMeals(patient);

        return mealDtoMapper.toDtoList(mealRepository.findAllByPatient_UserId(patientId));
    }

    @Transactional
    public List<MealResponseDto> getDietitianAllMeals(UUID dietitianId) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        mealAccessManager.checkCanGetDietitianAllMeals(dietitian);

        return mealDtoMapper.toDtoList(mealRepository.findAllByDietitian_UserId(dietitianId));
    }

    @Transactional
    public MealResponseDto createMeal(CreateMealRequestDto createMealRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Patient patient = patientRepository.findById(createMealRequestDto.getPatientId()).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        Dietitian dietitian = dietitianRepository.findById(createMealRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        mealAccessManager.checkCanCreateMeal(patient, dietitian);

        if (createMealRequestDto.getMealName() == null) {
            throw new IllegalArgumentException(MealMessages.MEAL_NAME_CANNOT_BE_NULL);
        }

        if (createMealRequestDto.getMealName().isEmpty()) {
            throw new IllegalArgumentException(MealMessages.MEAL_NAME_CANNOT_BE_EMPTY);
        }

        Meal meal = new Meal();

        meal.setMealName(createMealRequestDto.getMealName());
        meal.setDatetime(createMealRequestDto.getDatetime());
        meal.setPatient(patient);
        meal.setDietitian(dietitian);

        return mealDtoMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public MealResponseDto updateMealById(Long mealId, UpdateMealRequestDto updateMealRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(MealMessages.MEAL_NOT_FOUND);
        }

        mealAccessManager.checkCanUpdateMeal(meal);

        if (updateMealRequestDto.getMealName() != null) {
            if (updateMealRequestDto.getMealName().isEmpty()) {
                throw new IllegalArgumentException(MealMessages.MEAL_NAME_CANNOT_BE_EMPTY);
            }

            meal.setMealName(updateMealRequestDto.getMealName());
        }

        if (updateMealRequestDto.getDatetime() != null) {
            meal.setDatetime(updateMealRequestDto.getDatetime());
        }

        return mealDtoMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public void deleteMealById(Long mealId) throws EntityNotFoundException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(MealMessages.MEAL_NOT_FOUND);
        }

        mealAccessManager.checkCanDeleteMeal(meal);

        mealRepository.deleteById(mealId);
    }

}
