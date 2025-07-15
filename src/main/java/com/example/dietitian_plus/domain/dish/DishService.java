package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.auth.access.manager.DishAccessManager;
import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.request.CreateDishRequestDto;
import com.example.dietitian_plus.domain.dish.dto.request.UpdateDishRequestDto;
import com.example.dietitian_plus.domain.dish.dto.response.DishResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.DishProductService;
import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishProductResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishWithProductsResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final DietitianRepository dietitianRepository;

    private final DishDtoMapper dishDtoMapper;

    private final DishProductService dishProductService;

    private final DishAccessManager dishAccessManager;

    public List<DishResponseDto> getAllDishes() {
        return dishDtoMapper.toDtoList(dishRepository.findAll());
    }

    @Transactional
    public List<DishResponseDto> getAllPublicDishes() {
        List<Dish> publicDishes = dishRepository.findAllByPublic_True();

        return dishDtoMapper.toDtoList(publicDishes);
    }

    @Transactional
    public DishResponseDto getDishById(Long dishId) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        dishAccessManager.checkCanReadDish(dish);

        return dishDtoMapper.toDto(dish);
    }

    @Transactional
    public List<DishResponseDto> getDietitianAllDishes(UUID dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        dishAccessManager.checkCanReadDietitianAllDishes(dietitianId);

        return dishDtoMapper.toDtoList(dishRepository.findAllByDietitian_UserId(dietitianId));
    }

    @Transactional
    public DishWithProductsResponseDto createDish(CreateDishRequestDto createDishRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Dietitian dietitian = dietitianRepository.findById(createDishRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        Dish dish = new Dish();

        dish.setDishName(createDishRequestDto.getDishName().trim());
        dish.setTemplate(createDishRequestDto.isTemplate());
        dish.setPublic(createDishRequestDto.isPublic());
        dish.setRecipe(createDishRequestDto.getRecipe().trim());
        dish.setDietitian(dietitian);

        Dish savedDish = dishRepository.save(dish);

        List<DishProductResponseDto> dishProductResponseDtoList = dishProductService.addManyProductsToDish(savedDish.getDishId(), createDishRequestDto.getProducts());

        DishWithProductsResponseDto dishWithProductsResponseDto = new DishWithProductsResponseDto();

        dishWithProductsResponseDto.setDish(dishDtoMapper.toDto(savedDish));
        dishWithProductsResponseDto.setProducts(dishProductResponseDtoList);

        return dishWithProductsResponseDto;
    }

    @Transactional
    public DishResponseDto updateDishById(Long dishId, UpdateDishRequestDto updateDishRequestDto) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        dishAccessManager.checkCanUpdateDish(dish);

        if (updateDishRequestDto.getDishName() != null) {
            dish.setDishName(updateDishRequestDto.getDishName().trim());
        }

        if (updateDishRequestDto.getIsTemplate() != null) {
            dish.setTemplate(updateDishRequestDto.getIsTemplate());
        }

        if (updateDishRequestDto.getIsPublic() != null) {
            dish.setPublic(updateDishRequestDto.getIsPublic());
        }

        if (updateDishRequestDto.getRecipe() != null) {
            dish.setRecipe(updateDishRequestDto.getRecipe().trim());
        }

        return dishDtoMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public void deleteDishById(Long dishId) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        dishAccessManager.checkCanDeleteDish(dish);

        dishRepository.deleteById(dishId);
    }

}
