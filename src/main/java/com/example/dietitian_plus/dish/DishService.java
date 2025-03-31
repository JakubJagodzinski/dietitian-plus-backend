package com.example.dietitian_plus.dish;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
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

    private final String DISH_NOT_FOUND_MESSAGE = "Dish not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public DishService(DishRepository dishRepository, DietitianRepository dietitianRepository) {
        this.dishRepository = dishRepository;
        this.dietitianRepository = dietitianRepository;
    }

    public List<DishDto> getDishes() {
        List<Dish> dishes = dishRepository.findAll();
        List<DishDto> dishesDto = new ArrayList<>();

        for (Dish dish : dishes) {
            dishesDto.add(mapToDto(dish));
        }

        return dishesDto;
    }

    public DishDto getDishById(Long id) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        return mapToDto(dishRepository.getReferenceById(id));
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

        return mapToDto(dishRepository.save(dish));
    }

    @Transactional
    public DishDto updateDishById(Long id, UpdateDishDto updateDishDto) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        Dish dish = dishRepository.getReferenceById(id);

        if (dish.getDietitian() != null) {
            if (!dietitianRepository.existsById(updateDishDto.getDietitianId())) {
                throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
            }

            Dietitian dietitian = dietitianRepository.getReferenceById(updateDishDto.getDietitianId());
            dish.setDietitian(dietitian);
        }


        if (dish.getDishName() != null) {
            dish.setDishName(updateDishDto.getDishName());
        }

        if (dish.getRecipe() != null) {
            dish.setRecipe(updateDishDto.getRecipe());
        }

        if (dish.getIsVisible() != null) {
            dish.setIsVisible(updateDishDto.getIsVisible());
        }

        if (dish.getIsPublic() != null) {
            dish.setIsPublic(updateDishDto.getIsPublic());
        }

        return mapToDto(dishRepository.save(dish));
    }

    @Transactional
    public void deleteDishById(Long id) throws EntityNotFoundException {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        dishRepository.deleteById(id);
    }

    private DishDto mapToDto(Dish dish) {
        DishDto dishDto = new DishDto();

        dishDto.setDishId(dish.getDishId());
        dishDto.setDishName(dish.getDishName());
        dishDto.setIsVisible(dish.getIsVisible());
        dishDto.setIsPublic(dish.getIsPublic());
        dishDto.setKcal(dish.getKcal());
        dishDto.setFats(dish.getFats());
        dishDto.setCarbs(dish.getCarbs());
        dishDto.setProtein(dish.getProtein());
        dishDto.setFiber(dish.getFiber());
        dishDto.setRecipe(dish.getRecipe());

        return dishDto;
    }

}
