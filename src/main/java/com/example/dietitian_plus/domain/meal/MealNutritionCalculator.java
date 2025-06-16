package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.domain.meal.dto.NutritionValuesDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class MealNutritionCalculator {

    public NutritionValuesDto calculateMealsNutritionValues(List<Meal> meals) {
        NutritionValuesDto nutritionValuesDto = new NutritionValuesDto();

        float kcal = 0;
        float fats = 0;
        float carbs = 0;
        float protein = 0;
        float fiber = 0;
        float glycemicIndex = 0;
        float glycemicLoad = 0;

        for (Meal meal : meals) {
            kcal += meal.getKcal();
            fats += meal.getFats();
            carbs += meal.getCarbs();
            protein += meal.getProtein();
            fiber += meal.getFiber();
            glycemicIndex += meal.getGlycemicIndex();
            glycemicLoad += meal.getGlycemicLoad();
        }

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
