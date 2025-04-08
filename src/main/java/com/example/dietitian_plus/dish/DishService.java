package com.example.dietitian_plus.dish;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
import com.example.dietitian_plus.dishesproducts.DishesProducts;
import com.example.dietitian_plus.dishesproducts.DishesProductsRepository;
import com.example.dietitian_plus.product.Product;
import com.example.dietitian_plus.product.ProductDto;
import com.example.dietitian_plus.product.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final DietitianRepository dietitianRepository;
    private final DishesProductsRepository dishesProductsRepository;

    private final DishMapper dishMapper;
    private final ProductMapper productMapper;

    private final String DISH_NOT_FOUND_MESSAGE = "Dish not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public DishService(DishRepository dishRepository, DietitianRepository dietitianRepository, DishesProductsRepository dishesProductsRepository, DishMapper dishMapper, ProductMapper productMapper) {
        this.dishRepository = dishRepository;
        this.dietitianRepository = dietitianRepository;
        this.dishesProductsRepository = dishesProductsRepository;
        this.dishMapper = dishMapper;
        this.productMapper = productMapper;
    }

    public List<DishDto> getDishes() {
        List<Dish> dishes = dishRepository.findAll();
        List<DishDto> dishesDto = new ArrayList<>();

        for (Dish dish : dishes) {
            dishesDto.add(dishMapper.toDto(dish));
        }

        return dishesDto;
    }

    public DishDto getDishById(Long id) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        return dishMapper.toDto(dishRepository.getReferenceById(id));
    }

    public List<ProductDto> getDishProducts(Long id) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        List<DishesProducts> dishesProducts = dishesProductsRepository.findByDish_DishId(id);

        List<Product> products = dishesProducts.stream()
                .map(DishesProducts::getProduct)
                .toList();

        return productMapper.toDtoList(products);
    }

    @Transactional
    public DishDto createDish(CreateDishDto createDishDto) throws EntityNotFoundException {
        Dish dish = new Dish();

        if (!dietitianRepository.existsById(createDishDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.getReferenceById(createDishDto.getDietitianId());

        dish.setDietitian(dietitian);
        dish.setDishName(createDishDto.getDishName());

        return dishMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public DishDto updateDishById(Long id, UpdateDishDto updateDishDto) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        Dish dish = dishRepository.getReferenceById(id);

        if (updateDishDto.getDishName() != null) {
            dish.setDishName(updateDishDto.getDishName());
        }

        if (updateDishDto.getRecipe() != null) {
            dish.setRecipe(updateDishDto.getRecipe());
        }

        if (updateDishDto.getIsVisible() != null) {
            dish.setIsVisible(updateDishDto.getIsVisible());
        }

        if (updateDishDto.getIsPublic() != null) {
            dish.setIsPublic(updateDishDto.getIsPublic());
        }

        return dishMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public void deleteDishById(Long id) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        dishRepository.deleteById(id);
    }

}
