package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.dish.dto.CreateDishRequestDto;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.dish.dto.UpdateDishRequestDto;
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

    public List<DishResponseDto> getAllDishes() {
        return dishDtoMapper.toDtoList(dishRepository.findAll());
    }

    @Transactional
    public DishResponseDto getDishById(Long dishId) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        return dishDtoMapper.toDto(dish);
    }

    @Transactional
    public List<DishResponseDto> getDietitianAllDishes(UUID dietitianId) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        return dishDtoMapper.toDtoList(dishRepository.findAllByDietitian_UserId(dietitianId));
    }

    @Transactional
    public DishResponseDto createDish(CreateDishRequestDto createDishRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        if (createDishRequestDto.getDietitianId() == null) {
            throw new IllegalArgumentException(DietitianMessages.DIETITIAN_CANNOT_BE_NULL);
        }

        Dietitian dietitian = dietitianRepository.findById(createDishRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        Dish dish = new Dish();

        if (createDishRequestDto.getDishName() == null) {
            throw new IllegalArgumentException(DishMessages.DISH_NAME_CANNOT_BE_NULL);
        }

        if (createDishRequestDto.getDishName().isEmpty()) {
            throw new IllegalArgumentException(DishMessages.DISH_NAME_CANNOT_BE_EMPTY);
        }

        dish.setDishName(createDishRequestDto.getDishName());

        if (createDishRequestDto.getIsTemplate() != null) {
            dish.setIsTemplate(createDishRequestDto.getIsTemplate());
        }

        if (createDishRequestDto.getIsPublic() != null) {
            dish.setIsPublic(createDishRequestDto.getIsPublic());
        }

        dish.setRecipe(createDishRequestDto.getRecipe());

        dish.setDietitian(dietitian);

        return dishDtoMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public DishResponseDto updateDishById(Long dishId, UpdateDishRequestDto updateDishRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        if (updateDishRequestDto.getDishName() != null) {
            if (updateDishRequestDto.getDishName().isEmpty()) {
                throw new IllegalArgumentException(DishMessages.DISH_NAME_CANNOT_BE_EMPTY);
            }

            dish.setDishName(updateDishRequestDto.getDishName());
        }

        if (updateDishRequestDto.getIsTemplate() != null) {
            dish.setIsTemplate(updateDishRequestDto.getIsTemplate());
        }

        if (updateDishRequestDto.getIsPublic() != null) {
            dish.setIsPublic(updateDishRequestDto.getIsPublic());
        }

        if (updateDishRequestDto.getRecipe() != null) {
            dish.setRecipe(updateDishRequestDto.getRecipe());
        }

        return dishDtoMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public void deleteDishById(Long dishId) throws EntityNotFoundException {
        if (!dishRepository.existsById(dishId)) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        dishRepository.deleteById(dishId);
    }

}
