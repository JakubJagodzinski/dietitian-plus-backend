package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.dish.dto.CreateDishRequestDto;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.UpdateDishRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.DishesProducts;
import com.example.dietitian_plus.domain.dishesproducts.DishesProductsRepository;
import com.example.dietitian_plus.domain.product.Product;
import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
import com.example.dietitian_plus.domain.product.dto.ProductDtoMapper;
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

    private final DishDtoMapper dishDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    private static final String DISH_NOT_FOUND_MESSAGE = "Dish not found";
    private static final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public DishService(DishRepository dishRepository, DietitianRepository dietitianRepository, DishesProductsRepository dishesProductsRepository, DishDtoMapper dishDtoMapper, ProductDtoMapper productDtoMapper) {
        this.dishRepository = dishRepository;
        this.dietitianRepository = dietitianRepository;
        this.dishesProductsRepository = dishesProductsRepository;
        this.dishDtoMapper = dishDtoMapper;
        this.productDtoMapper = productDtoMapper;
    }

    public List<DishResponseDto> getDishes() {
        List<Dish> dishes = dishRepository.findAll();
        List<DishResponseDto> dishesDto = new ArrayList<>();

        for (Dish dish : dishes) {
            dishesDto.add(dishDtoMapper.toDto(dish));
        }

        return dishesDto;
    }

    public DishResponseDto getDishById(Long id) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        return dishDtoMapper.toDto(dishRepository.getReferenceById(id));
    }

    public List<ProductResponseDto> getDishProducts(Long id) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        List<DishesProducts> dishesProducts = dishesProductsRepository.findByDish_DishId(id);

        List<Product> products = dishesProducts.stream()
                .map(DishesProducts::getProduct)
                .toList();

        return productDtoMapper.toDtoList(products);
    }

    @Transactional
    public DishResponseDto createDish(CreateDishRequestDto createDishRequestDto) throws EntityNotFoundException {
        Dish dish = new Dish();

        if (!dietitianRepository.existsById(createDishRequestDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.getReferenceById(createDishRequestDto.getDietitianId());

        dish.setDietitian(dietitian);
        dish.setDishName(createDishRequestDto.getDishName());

        return dishDtoMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public DishResponseDto updateDishById(Long id, UpdateDishRequestDto updateDishRequestDto) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        Dish dish = dishRepository.getReferenceById(id);

        if (updateDishRequestDto.getDishName() != null) {
            dish.setDishName(updateDishRequestDto.getDishName());
        }

        if (updateDishRequestDto.getRecipe() != null) {
            dish.setRecipe(updateDishRequestDto.getRecipe());
        }

        if (updateDishRequestDto.getIsVisible() != null) {
            dish.setIsVisible(updateDishRequestDto.getIsVisible());
        }

        if (updateDishRequestDto.getIsPublic() != null) {
            dish.setIsPublic(updateDishRequestDto.getIsPublic());
        }

        return dishDtoMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public void deleteDishById(Long id) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        dishRepository.deleteById(id);
    }

}
