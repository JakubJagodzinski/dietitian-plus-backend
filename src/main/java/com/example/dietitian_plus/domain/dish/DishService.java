package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.common.constants.Messages;
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
            throw new EntityNotFoundException(Messages.DISH_NOT_FOUND);
        }

        return dishDtoMapper.toDto(dish);
    }

    @Transactional
    public List<DishResponseDto> getDietitianAllDishes(Long dietitianId) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        return dishDtoMapper.toDtoList(dishRepository.findAllByDietitian_Id(dietitianId));
    }

    @Transactional
    public DishResponseDto createDish(CreateDishRequestDto createDishRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        if (createDishRequestDto.getDietitianId() == null) {
            throw new IllegalArgumentException(Messages.DIETITIAN_CANNOT_BE_NULL);
        }

        Dietitian dietitian = dietitianRepository.findById(createDishRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        Dish dish = new Dish();

        if (createDishRequestDto.getDishName() == null) {
            throw new IllegalArgumentException(Messages.DISH_NAME_CANNOT_BE_NULL);
        }

        if (createDishRequestDto.getDishName().isEmpty()) {
            throw new IllegalArgumentException(Messages.DISH_NAME_CANNOT_BE_EMPTY);
        }

        dish.setDishName(createDishRequestDto.getDishName());

        if (createDishRequestDto.getIsTemplate() != null) {
            dish.setIsTemplate(createDishRequestDto.getIsTemplate());
        }

        if (createDishRequestDto.getIsPublic() != null) {
            dish.setIsPublic(createDishRequestDto.getIsPublic());
        }

        dish.setRecipe(createDishRequestDto.getRecipe());

        if (createDishRequestDto.getKcal() != null) {
            if (createDishRequestDto.getKcal() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setKcal(createDishRequestDto.getKcal());
        }

        if (createDishRequestDto.getFats() != null) {
            if (createDishRequestDto.getFats() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setFats(createDishRequestDto.getFats());
        }

        if (createDishRequestDto.getCarbs() != null) {
            if (createDishRequestDto.getCarbs() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setCarbs(createDishRequestDto.getCarbs());
        }

        if (createDishRequestDto.getProtein() != null) {
            if (createDishRequestDto.getProtein() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setProtein(createDishRequestDto.getProtein());
        }

        if (createDishRequestDto.getFiber() != null) {
            if (createDishRequestDto.getFiber() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setFiber(createDishRequestDto.getFiber());
        }

        if (createDishRequestDto.getGlycemicIndex() != null) {
            if (createDishRequestDto.getGlycemicIndex() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setGlycemicIndex(createDishRequestDto.getGlycemicIndex());
        }

        if (createDishRequestDto.getGlycemicLoad() != null) {
            if (createDishRequestDto.getGlycemicLoad() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setGlycemicLoad(createDishRequestDto.getGlycemicLoad());
        }

        dish.setDietitian(dietitian);

        return dishDtoMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public DishResponseDto updateDishById(Long dishId, UpdateDishRequestDto updateDishRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(Messages.DISH_NOT_FOUND);
        }

        if (updateDishRequestDto.getDishName() != null) {
            if (updateDishRequestDto.getDishName().isEmpty()) {
                throw new IllegalArgumentException(Messages.DISH_NAME_CANNOT_BE_EMPTY);
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

        if (updateDishRequestDto.getKcal() != null) {
            if (updateDishRequestDto.getKcal() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setKcal(updateDishRequestDto.getKcal());
        }

        if (updateDishRequestDto.getFats() != null) {
            if (updateDishRequestDto.getFats() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setFats(updateDishRequestDto.getFats());
        }

        if (updateDishRequestDto.getCarbs() != null) {
            if (updateDishRequestDto.getCarbs() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setCarbs(updateDishRequestDto.getCarbs());
        }

        if (updateDishRequestDto.getProtein() != null) {
            if (updateDishRequestDto.getProtein() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setProtein(updateDishRequestDto.getProtein());
        }

        if (updateDishRequestDto.getFiber() != null) {
            if (updateDishRequestDto.getFiber() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setFiber(updateDishRequestDto.getFiber());
        }

        if (updateDishRequestDto.getGlycemicIndex() != null) {
            if (updateDishRequestDto.getGlycemicIndex() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setGlycemicIndex(updateDishRequestDto.getGlycemicIndex());
        }

        if (updateDishRequestDto.getGlycemicLoad() != null) {
            if (updateDishRequestDto.getGlycemicLoad() < 0) {
                throw new IllegalArgumentException(Messages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            dish.setGlycemicLoad(updateDishRequestDto.getGlycemicLoad());
        }

        return dishDtoMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public void deleteDishById(Long dishId) throws EntityNotFoundException {
        if (!dishRepository.existsById(dishId)) {
            throw new EntityNotFoundException(Messages.DISH_NOT_FOUND);
        }

        dishRepository.deleteById(dishId);
    }

}
