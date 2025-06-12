package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.common.constants.Messages;
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

    public List<MealResponseDto> getAllMeals() {
        return mealDtoMapper.toDtoList(mealRepository.findAll());
    }

    @Transactional
    public MealResponseDto getMealById(Long mealId) throws EntityNotFoundException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(Messages.MEAL_NOT_FOUND);
        }

        return mealDtoMapper.toDto(meal);
    }

    @Transactional
    public List<MealResponseDto> getPatientAllMeals(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND);
        }

        return mealDtoMapper.toDtoList(mealRepository.findAllByPatient_Id(patientId));
    }

    @Transactional
    public List<MealResponseDto> getDietitianAllMeals(Long dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        return mealDtoMapper.toDtoList(mealRepository.findAllByDietitian_Id(dietitianId));
    }

    @Transactional
    public MealResponseDto createMeal(CreateMealRequestDto createMealRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Patient patient = patientRepository.findById(createMealRequestDto.getPatientId()).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND);
        }

        Dietitian dietitian = dietitianRepository.findById(createMealRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        if (createMealRequestDto.getMealName() == null) {
            throw new IllegalArgumentException(Messages.MEAL_NAME_CANNOT_BE_NULL);
        }

        if (createMealRequestDto.getMealName().isEmpty()) {
            throw new IllegalArgumentException(Messages.MEAL_NAME_CANNOT_BE_EMPTY);
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
            throw new EntityNotFoundException(Messages.MEAL_NOT_FOUND);
        }

        if (updateMealRequestDto.getMealName() != null) {
            if (updateMealRequestDto.getMealName().isEmpty()) {
                throw new IllegalArgumentException(Messages.MEAL_NAME_CANNOT_BE_EMPTY);
            }

            meal.setMealName(updateMealRequestDto.getMealName());
        }

        if (updateMealRequestDto.getDatetime() != null) {
            meal.setDatetime(updateMealRequestDto.getDatetime());
        }

        if (updateMealRequestDto.getPatientId() != null) {
            Patient patient = patientRepository.findById(updateMealRequestDto.getPatientId()).orElse(null);

            if (patient == null) {
                throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND);
            }

            meal.setPatient(patient);
        }

        if (updateMealRequestDto.getDietitianId() != null) {
            Dietitian dietitian = dietitianRepository.findById(updateMealRequestDto.getDietitianId()).orElse(null);

            if (dietitian == null) {
                throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
            }

            meal.setDietitian(dietitian);
        }

        return mealDtoMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public void deleteMealById(Long mealId) throws EntityNotFoundException {
        if (!mealRepository.existsById(mealId)) {
            throw new EntityNotFoundException(Messages.MEAL_NOT_FOUND);
        }

        mealRepository.deleteById(mealId);
    }

}
