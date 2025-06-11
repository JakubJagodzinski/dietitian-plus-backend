package com.example.dietitian_plus.domain.mealsdishes;

import com.example.dietitian_plus.common.Messages;
import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.dish.DishRepository;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.meal.Meal;
import com.example.dietitian_plus.domain.meal.MealRepository;
import com.example.dietitian_plus.domain.mealsdishes.dto.CreateMealDishRequestDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.MealDishDtoMapper;
import com.example.dietitian_plus.domain.mealsdishes.dto.MealDishResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealDishService {

    private final MealDishRepository mealDishRepository;
    private final MealRepository mealRepository;
    private final DishRepository dishRepository;

    private final MealDishDtoMapper mealDishDtoMapper;
    private final DishDtoMapper dishDtoMapper;

    @Transactional
    public List<DishResponseDto> getMealAllDishes(Long mealId) throws EntityNotFoundException {
        if (!mealRepository.existsById(mealId)) {
            throw new EntityNotFoundException(Messages.MEAL_NOT_FOUND);
        }

        List<MealDish> mealDishList = mealDishRepository.findAllByMeal_MealId(mealId);

        return mealDishList.stream()
                .map(MealDish::getDish)
                .map(dishDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public MealDishResponseDto addDishToMeal(Long mealId, CreateMealDishRequestDto createMealDishRequestDto) throws EntityNotFoundException, EntityExistsException {
        Meal meal = mealRepository.findById(mealId).orElse(null);

        if (meal == null) {
            throw new EntityNotFoundException(Messages.MEAL_NOT_FOUND);
        }

        Dish dish = dishRepository.findById(createMealDishRequestDto.getDishId()).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(Messages.DISH_NOT_FOUND);
        }

        MealDishId mealDishId = new MealDishId(meal.getMealId(), dish.getDishId());

        if (mealDishRepository.existsById(mealDishId)) {
            throw new EntityExistsException(Messages.DISH_ALREADY_ASSIGNED_TO_MEAL);
        }

        MealDish mealDish = new MealDish();

        mealDish.setId(mealDishId);
        mealDish.setMeal(meal);
        mealDish.setDish(dish);
        mealDish.setDishQuantity(createMealDishRequestDto.getDishQuantity());

        return mealDishDtoMapper.toDto(mealDishRepository.save(mealDish));
    }

    @Transactional
    public void removeDishFromMeal(Long mealId, Long dishId) throws EntityNotFoundException {
        MealDish mealDish = mealDishRepository.findById(new MealDishId(mealId, dishId)).orElse(null);

        if (mealDish == null) {
            throw new EntityNotFoundException(Messages.DISH_NOT_ASSIGNED_TO_MEAL);
        }

        mealDishRepository.delete(mealDish);
    }

}
