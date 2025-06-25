package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.response.DishResponseDto;
import com.example.dietitian_plus.domain.meal.dto.response.NutritionValuesResponseDto;
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

    public NutritionValuesResponseDto calculateMealsNutritionValues(List<Meal> meals) {
        double kcal = 0;
        double fats = 0;
        double carbs = 0;
        double protein = 0;
        double fiber = 0;
        double glycemicIndex = 0;
        double glycemicLoad = 0;

        for (Meal meal : meals) {
            NutritionValuesResponseDto mealNutritionValues = calculateMealNutritionValues(meal);

            kcal += mealNutritionValues.getKcal();
            fats += mealNutritionValues.getFats();
            carbs += mealNutritionValues.getCarbs();
            protein += mealNutritionValues.getProtein();
            fiber += mealNutritionValues.getFiber();
            glycemicIndex += mealNutritionValues.getGlycemicIndex();
            glycemicLoad += mealNutritionValues.getGlycemicLoad();
        }

        NutritionValuesResponseDto nutritionValuesResponseDto = new NutritionValuesResponseDto();

        nutritionValuesResponseDto.setKcal(kcal);
        nutritionValuesResponseDto.setFats(fats);
        nutritionValuesResponseDto.setCarbs(carbs);
        nutritionValuesResponseDto.setProtein(protein);
        nutritionValuesResponseDto.setFiber(fiber);
        nutritionValuesResponseDto.setGlycemicIndex(glycemicIndex);
        nutritionValuesResponseDto.setGlycemicLoad(glycemicLoad);

        return nutritionValuesResponseDto;
    }

    private List<DishResponseDto> getMealAllDishes(Long mealId) {
        List<MealDish> mealDishList = mealDishRepository.findAllByMeal_MealId(mealId);

        return mealDishList.stream()
                .map(MealDish::getDish)
                .map(dishDtoMapper::toDto)
                .toList();
    }

    public NutritionValuesResponseDto calculateMealNutritionValues(Meal meal) {
        List<DishResponseDto> mealDishes = getMealAllDishes(meal.getMealId());

        double kcal = 0;
        double fats = 0;
        double carbs = 0;
        double protein = 0;
        double fiber = 0;
        double glycemicIndex = 0;
        double glycemicLoad = 0;

        for (DishResponseDto dish : mealDishes) {
            NutritionValuesResponseDto dishNutritionValues = dish.getNutritionValues();

            kcal += dishNutritionValues.getKcal();
            fats += dishNutritionValues.getFats();
            carbs += dishNutritionValues.getCarbs();
            protein += dishNutritionValues.getProtein();
            fiber += dishNutritionValues.getFiber();
            glycemicIndex += dishNutritionValues.getGlycemicIndex();
            glycemicLoad += dishNutritionValues.getGlycemicLoad();
        }

        NutritionValuesResponseDto nutritionValuesResponseDto = new NutritionValuesResponseDto();

        nutritionValuesResponseDto.setKcal(kcal);
        nutritionValuesResponseDto.setFats(fats);
        nutritionValuesResponseDto.setCarbs(carbs);
        nutritionValuesResponseDto.setProtein(protein);
        nutritionValuesResponseDto.setFiber(fiber);
        nutritionValuesResponseDto.setGlycemicIndex(glycemicIndex);
        nutritionValuesResponseDto.setGlycemicLoad(glycemicLoad);

        return nutritionValuesResponseDto;
    }

}
