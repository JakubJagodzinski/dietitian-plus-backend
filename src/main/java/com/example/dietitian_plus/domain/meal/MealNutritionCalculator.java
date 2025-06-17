package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.meal.dto.NutritionValuesDto;
import com.example.dietitian_plus.domain.mealsdishes.MealDish;
import com.example.dietitian_plus.domain.mealsdishes.MealDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealNutritionCalculator {

    private final MealDishRepository mealDishRepository;

    private final DishDtoMapper dishDtoMapper;

    public NutritionValuesDto calculateMealsNutritionValues(List<Meal> meals) {
        float kcal = 0;
        float fats = 0;
        float carbs = 0;
        float protein = 0;
        float fiber = 0;
        float glycemicIndex = 0;
        float glycemicLoad = 0;

        for (Meal meal : meals) {
            NutritionValuesDto mealNutritionValues = calculateMealNutritionValues(meal);

            kcal += mealNutritionValues.getKcal();
            fats += mealNutritionValues.getFats();
            carbs += mealNutritionValues.getCarbs();
            protein += mealNutritionValues.getProtein();
            fiber += mealNutritionValues.getFiber();
            glycemicIndex += mealNutritionValues.getGlycemicIndex();
            glycemicLoad += mealNutritionValues.getGlycemicLoad();
        }

        NutritionValuesDto nutritionValuesDto = new NutritionValuesDto();

        nutritionValuesDto.setKcal(kcal);
        nutritionValuesDto.setFats(fats);
        nutritionValuesDto.setCarbs(carbs);
        nutritionValuesDto.setProtein(protein);
        nutritionValuesDto.setFiber(fiber);
        nutritionValuesDto.setGlycemicIndex(glycemicIndex);
        nutritionValuesDto.setGlycemicLoad(glycemicLoad);

        return nutritionValuesDto;
    }

    private List<DishResponseDto> getMealAllDishes(Long mealId) {
        List<MealDish> mealDishList = mealDishRepository.findAllByMeal_MealId(mealId);

        return mealDishList.stream()
                .map(MealDish::getDish)
                .map(dishDtoMapper::toDto)
                .toList();
    }

    public NutritionValuesDto calculateMealNutritionValues(Meal meal) {
        List<DishResponseDto> mealDishes = getMealAllDishes(meal.getMealId());

        float kcal = 0;
        float fats = 0;
        float carbs = 0;
        float protein = 0;
        float fiber = 0;
        float glycemicIndex = 0;
        float glycemicLoad = 0;

        for (DishResponseDto dish : mealDishes) {
            NutritionValuesDto dishNutritionValues = dish.getNutritionValues();

            kcal += dishNutritionValues.getKcal();
            fats += dishNutritionValues.getFats();
            carbs += dishNutritionValues.getCarbs();
            protein += dishNutritionValues.getProtein();
            fiber += dishNutritionValues.getFiber();
            glycemicIndex += dishNutritionValues.getGlycemicIndex();
            glycemicLoad += dishNutritionValues.getGlycemicLoad();
        }

        NutritionValuesDto nutritionValuesDto = new NutritionValuesDto();

        nutritionValuesDto.setKcal(kcal);
        nutritionValuesDto.setFats(fats);
        nutritionValuesDto.setCarbs(carbs);
        nutritionValuesDto.setProtein(protein);
        nutritionValuesDto.setFiber(fiber);
        nutritionValuesDto.setGlycemicIndex(glycemicIndex);
        nutritionValuesDto.setGlycemicLoad(glycemicLoad);

        return nutritionValuesDto;
    }

}
