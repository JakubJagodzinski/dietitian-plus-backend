package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.example.dietitian_plus.domain.dishesproducts.DishProduct;
import com.example.dietitian_plus.domain.dishesproducts.DishProductRepository;
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

    public void increaseDishNutritionValues(DishProduct dishProduct) {
        Dish dish = dishProduct.getDish();
        Product product = dishProduct.getProduct();
        float grams = dishProduct.getUnit().getGrams() * dishProduct.getUnitCount() / 100.0f;

        dish.setKcal(dish.getKcal() + (product.getKcal() * grams));
        dish.setFats(dish.getFats() + (product.getFats() * grams));
        dish.setCarbs(dish.getCarbs() + (product.getCarbs() * grams));
        dish.setProtein(dish.getProtein() + (product.getProtein() * grams));
        dish.setFiber(dish.getFiber() + (product.getFiber() * grams));
        dish.setGlycemicIndex(dish.getGlycemicIndex() + (product.getGlycemicIndex() * grams));
        dish.setGlycemicLoad(dish.getGlycemicLoad() + (product.getGlycemicLoad() * grams));

        dishRepository.save(dish);
    }

    public void decreaseDishNutritionValues(DishProduct dishProduct) {
        Dish dish = dishProduct.getDish();
        Product product = dishProduct.getProduct();
        float grams = dishProduct.getUnit().getGrams() * dishProduct.getUnitCount() / 100.0f;

        dish.setKcal(dish.getKcal() - (product.getKcal() * grams));
        dish.setFats(dish.getFats() - (product.getFats() * grams));
        dish.setCarbs(dish.getCarbs() - (product.getCarbs() * grams));
        dish.setProtein(dish.getProtein() - (product.getProtein() * grams));
        dish.setFiber(dish.getFiber() - (product.getFiber() * grams));
        dish.setGlycemicIndex(dish.getGlycemicIndex() - (product.getGlycemicIndex() * grams));
        dish.setGlycemicLoad(dish.getGlycemicLoad() - (product.getGlycemicLoad() * grams));

        dishRepository.save(dish);
    }

    public void setDishNutritionValues(Long dishId) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        List<DishProduct> dishProductList = dishProductRepository.findAllByDish_DishId(dishId);

        float kcal = 0.0f;
        float fats = 0.0f;
        float carbs = 0.0f;
        float protein = 0.0f;
        float fiber = 0.0f;
        float glycemicIndex = 0.0f;
        float glycemicLoad = 0.0f;

        for (DishProduct dishProduct : dishProductList) {
            Product product = dishProduct.getProduct();

            float grams = dishProduct.getUnit().getGrams() * dishProduct.getUnitCount() / 100.0f;

            kcal += product.getKcal() * grams;
            fats += product.getFats() * grams;
            carbs += product.getCarbs() * grams;
            protein += product.getProtein() * grams;
            fiber += product.getFiber() * grams;
            glycemicIndex += product.getGlycemicIndex() * grams;
            glycemicLoad += product.getGlycemicLoad() * grams;
        }

        dish.setKcal(kcal);
        dish.setFats(fats);
        dish.setCarbs(carbs);
        dish.setProtein(protein);
        dish.setFiber(fiber);
        dish.setGlycemicIndex(glycemicIndex);
        dish.setGlycemicLoad(glycemicLoad);

        dishRepository.save(dish);
    }

}
