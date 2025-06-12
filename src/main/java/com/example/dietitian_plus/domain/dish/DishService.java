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
    public DishResponseDto createDish(CreateDishRequestDto createDishRequestDto) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(createDishRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        Dish dish = new Dish();

        dish.setIsPublic(createDishRequestDto.getIsPublic());
        dish.setIsTemplate(createDishRequestDto.getIsTemplate());
        dish.setDietitian(dietitian);
        dish.setDishName(createDishRequestDto.getDishName());
        dish.setRecipe(createDishRequestDto.getRecipe());
        dish.setKcal(createDishRequestDto.getKcal());
        dish.setFats(createDishRequestDto.getFats());
        dish.setCarbs(createDishRequestDto.getCarbs());
        dish.setProtein(createDishRequestDto.getProtein());
        dish.setFiber(createDishRequestDto.getFiber());

        return dishDtoMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public DishResponseDto updateDishById(Long dishId, UpdateDishRequestDto updateDishRequestDto) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(Messages.DISH_NOT_FOUND);
        }

        if (updateDishRequestDto.getIsPublic() != null) {
            dish.setIsPublic(updateDishRequestDto.getIsPublic());
        }

        if (updateDishRequestDto.getIsTemplate() != null) {
            dish.setIsTemplate(updateDishRequestDto.getIsTemplate());
        }

        if (updateDishRequestDto.getDishName() != null) {
            dish.setDishName(updateDishRequestDto.getDishName());
        }

        if (updateDishRequestDto.getRecipe() != null) {
            dish.setRecipe(updateDishRequestDto.getRecipe());
        }

        if (updateDishRequestDto.getKcal() != null) {
            dish.setKcal(updateDishRequestDto.getKcal());
        }

        if (updateDishRequestDto.getFats() != null) {
            dish.setFats(updateDishRequestDto.getFats());
        }

        if (updateDishRequestDto.getCarbs() != null) {
            dish.setCarbs(updateDishRequestDto.getCarbs());
        }

        if (updateDishRequestDto.getProtein() != null) {
            dish.setProtein(updateDishRequestDto.getProtein());
        }

        if (updateDishRequestDto.getFiber() != null) {
            dish.setFiber(updateDishRequestDto.getFiber());
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
