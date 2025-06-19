package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.example.dietitian_plus.domain.dishesproducts.DishProduct;
import com.example.dietitian_plus.domain.dishesproducts.DishProductRepository;
import com.example.dietitian_plus.domain.meal.dto.NutritionValuesDto;
import com.example.dietitian_plus.domain.product.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DishNutritionCalculator {

    private final DishRepository dishRepository;
    private final DishProductRepository dishProductRepository;

    public NutritionValuesDto calculateDishNutritionValues(Long dishId) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        List<DishProduct> dishProductList = dishProductRepository.findAllByDish_DishId(dishId);

        double kcal = 0.0;
        double fats = 0.0;
        double carbs = 0.0;
        double protein = 0.0;
        double fiber = 0.0;

        double totalNetCarbs = 0.0;
        double weightedSum = 0.0;

        for (DishProduct dishProduct : dishProductList) {
            Product product = dishProduct.getProduct();

            double grams = dishProduct.getUnit().getGrams() * dishProduct.getUnitCount() / 100.0;

            double totalGrams = dishProduct.getUnitCount() * dishProduct.getUnit().getGrams();

            double carbsPer100g = product.getCarbs();
            double fiberPer100g = product.getFiber();

            double netCarbs = Math.max((carbsPer100g - fiberPer100g) * (totalGrams / 100.0), 0.0);

            totalNetCarbs += netCarbs;
            weightedSum += netCarbs * product.getGlycemicIndex();

            kcal += product.getKcal() * grams;
            fats += product.getFats() * grams;
            carbs += product.getCarbs() * grams;
            protein += product.getProtein() * grams;
            fiber += product.getFiber() * grams;
        }

        double glycemicIndex;
        double glycemicLoad;

        if (totalNetCarbs == 0) {
            glycemicIndex = 0.0;
            glycemicLoad = 0.0;
        } else {
            glycemicIndex = weightedSum / totalNetCarbs;
            glycemicLoad = (glycemicIndex * totalNetCarbs) / 100.0;
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
