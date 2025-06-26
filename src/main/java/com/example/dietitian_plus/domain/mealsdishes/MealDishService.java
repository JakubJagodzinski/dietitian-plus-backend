package com.example.dietitian_plus.domain.mealsdishes;

import com.example.dietitian_plus.auth.access.manager.DishAccessManager;
import com.example.dietitian_plus.auth.access.manager.MealAccessManager;
import com.example.dietitian_plus.auth.access.manager.MealDishAccessManager;
import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.example.dietitian_plus.common.constants.messages.MealMessages;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.dish.DishRepository;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.response.DishResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.DishProductService;
import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishWithProductsResponseDto;
import com.example.dietitian_plus.domain.meal.Meal;
import com.example.dietitian_plus.domain.meal.MealNutritionCalculator;
import com.example.dietitian_plus.domain.meal.MealRepository;
import com.example.dietitian_plus.domain.meal.dto.MealDtoMapper;
import com.example.dietitian_plus.domain.meal.dto.response.NutritionValuesResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.*;
import com.example.dietitian_plus.domain.mealsdishes.dto.request.AddDishToMealRequestDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.MealDishResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.MealWithDishesResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.PatientDayMealsWithDishesResponseDto;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MealDishService {

    private final MealDishRepository mealDishRepository;
    private final MealRepository mealRepository;
    private final DishRepository dishRepository;
    private final PatientRepository patientRepository;

    private final DishProductService dishProductService;

    private final MealDishDtoMapper mealDishDtoMapper;
    private final DishDtoMapper dishDtoMapper;
    private final MealDtoMapper mealDtoMapper;

    private final MealDishAccessManager mealDishAccessManager;
    private final DishAccessManager dishAccessManager;
    private final MealAccessManager mealAccessManager;

    private final MealNutritionCalculator mealNutritionCalculator;

    @Transactional
    public List<DishResponseDto> getMealAllDishes(Long mealId) throws EntityNotFoundException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(MealMessages.MEAL_NOT_FOUND);
        }

        mealDishAccessManager.checkCanReadMealAllDishes(meal);

        List<MealDish> mealDishList = mealDishRepository.findAllByMeal_MealId(mealId);

        return mealDishList.stream()
                .map(MealDish::getDish)
                .map(dishDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PatientDayMealsWithDishesResponseDto getPatientDayMealsWithDishes(LocalDate date, UUID patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        mealAccessManager.checkCanReadPatientAllMeals(patient);

        PatientDayMealsWithDishesResponseDto patientDayMealsWithDishesResponseDto = new PatientDayMealsWithDishesResponseDto();

        patientDayMealsWithDishesResponseDto.setPatientId(patientId);
        patientDayMealsWithDishesResponseDto.setDate(date);

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<MealWithDishesResponseDto> mealWithDishesResponseDtoList = new ArrayList<>();

        List<Meal> meals = mealRepository.findAllByPatient_UserIdAndDatetimeBetween(patientId, startOfDay, endOfDay);

        for (Meal meal : meals) {
            mealWithDishesResponseDtoList.add(getMealAllDishesWithProducts(meal.getMealId()));
        }

        patientDayMealsWithDishesResponseDto.setMeals(mealWithDishesResponseDtoList);

        NutritionValuesResponseDto dailyNutritionValues = mealNutritionCalculator.calculateMealsNutritionValues(meals);

        patientDayMealsWithDishesResponseDto.setDailyNutritionValues(dailyNutritionValues);

        return patientDayMealsWithDishesResponseDto;
    }

    @Transactional
    public MealWithDishesResponseDto getMealAllDishesWithProducts(Long mealId) throws EntityNotFoundException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(MealMessages.MEAL_NOT_FOUND);
        }

        mealDishAccessManager.checkCanReadMealAllDishes(meal);

        List<MealDish> mealDishList = mealDishRepository.findAllByMeal_MealId(mealId);

        MealWithDishesResponseDto mealWithDishesResponseDto = new MealWithDishesResponseDto();

        mealWithDishesResponseDto.setMeal(mealDtoMapper.toDto(meal));

        List<DishWithProductsResponseDto> dishes = mealDishList.stream()
                .map(mealDish -> dishProductService.getDishWithProducts(mealDish.getDish().getDishId()))
                .toList();

        mealWithDishesResponseDto.setDishes(dishes);

        return mealWithDishesResponseDto;
    }

    @Transactional
    public MealDishResponseDto addDishToMeal(Long mealId, AddDishToMealRequestDto addDishToMealRequestDto) throws EntityNotFoundException, EntityExistsException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(MealMessages.MEAL_NOT_FOUND);
        }

        Dish dish = dishRepository.findById(addDishToMealRequestDto.getDishId()).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        dishAccessManager.checkIsDietitianDishOwnerRequest(dish);

        mealDishAccessManager.checkCanAddDishToMeal(meal);

        MealDishId mealDishId = new MealDishId(meal.getMealId(), dish.getDishId());

        if (mealDishRepository.existsById(mealDishId)) {
            throw new EntityExistsException(DishMessages.DISH_ALREADY_ASSIGNED_TO_MEAL);
        }

        MealDish mealDish = new MealDish();

        mealDish.setMealDishId(mealDishId);
        mealDish.setMeal(meal);
        mealDish.setDish(dish);
        mealDish.setDishQuantity(addDishToMealRequestDto.getDishQuantity());

        return mealDishDtoMapper.toDto(mealDishRepository.save(mealDish));
    }

    @Transactional
    public void removeDishFromMeal(Long mealId, Long dishId) throws EntityNotFoundException {
        MealDish mealDish = mealDishRepository.findById(new MealDishId(mealId, dishId)).orElse(null);

        if (mealDish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_ASSIGNED_TO_MEAL);
        }

        mealDishAccessManager.checkCanRemoveDishFromMeal(mealDish.getMeal());

        mealDishRepository.delete(mealDish);
    }

}
