package com.example.dietitian_plus.domain.dish;

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

    private static final String DISH_NOT_FOUND_MESSAGE = "Dish not found";
    private static final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    public List<DishResponseDto> getDishes() {
        return dishDtoMapper.toDtoList(dishRepository.findAll());
    }

    @Transactional
    public DishResponseDto getDishById(Long id) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        return dishDtoMapper.toDto(dishRepository.getReferenceById(id));
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
