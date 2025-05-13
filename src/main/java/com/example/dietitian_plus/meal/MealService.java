package com.example.dietitian_plus.meal;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
import com.example.dietitian_plus.dish.DishDto;
import com.example.dietitian_plus.dish.DishMapper;
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

    private final MealMapper mealMapper;
    private final DishMapper dishMapper;

    private final String MEAL_NOT_FOUND_MESSAGE = "Meal not found";
    private final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public MealService(MealRepository mealRepository, PatientRepository patientRepository, DietitianRepository dietitianRepository, MealMapper mealMapper, DishMapper dishMapper) {
        this.mealRepository = mealRepository;
        this.patientRepository = patientRepository;
        this.dietitianRepository = dietitianRepository;
        this.mealMapper = mealMapper;
        this.dishMapper = dishMapper;
    }

    public List<MealDto> getMeals() {
        List<Meal> meals = mealRepository.findAll();
        List<MealDto> mealsDto = new ArrayList<>();

        for (Meal meal : meals) {
            mealsDto.add(mealMapper.toDto(meal));
        }

        return mealsDto;
    }

    public List<DishDto> getMealDishes(Long id) throws EntityNotFoundException {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        Meal meal = mealRepository.getReferenceById(id);

        return dishMapper.toDtoList(meal.getDishes());
    }

    public MealDto getMealById(Long id) throws EntityNotFoundException {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException(MEAL_NOT_FOUND_MESSAGE);
        }

        return mealMapper.toDto(mealRepository.getReferenceById(id));
    }

    @Transactional
    public MealDto createMeal(CreateMealDto createMealDto) throws EntityNotFoundException {
        if (!patientRepository.existsById(createMealDto.getPatientId())) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!dietitianRepository.existsById(createMealDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Meal meal = new Meal();

        Patient patient = patientRepository.getReferenceById(createMealDto.getPatientId());
        meal.setPatient(patient);

        Dietitian dietitian = dietitianRepository.getReferenceById(createMealDto.getDietitianId());
        meal.setDietitian(dietitian);

        return mealMapper.toDto(mealRepository.save(meal));
    }

    @Transactional
    public MealDto updateMealById(Long id, CreateMealDto updateMealDto) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
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
