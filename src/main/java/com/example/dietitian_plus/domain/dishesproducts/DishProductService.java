package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.dish.DishRepository;
import com.example.dietitian_plus.domain.dishesproducts.dto.CreateDishProductRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.DishProductDtoMapper;
import com.example.dietitian_plus.domain.dishesproducts.dto.DishProductResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.UpdateDishProductRequestDto;
import com.example.dietitian_plus.domain.product.Product;
import com.example.dietitian_plus.domain.product.ProductRepository;
import com.example.dietitian_plus.domain.unit.Unit;
import com.example.dietitian_plus.domain.unit.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishProductService {

    private final DishProductRepository dishProductRepository;
    private final DishRepository dishRepository;
    private final ProductRepository productRepository;
    private final UnitRepository unitRepository;

    private final DishProductDtoMapper dishProductDtoMapper;

    private static final String DISH_NOT_FOUND_MESSAGE = "Dish not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String UNIT_NOT_FOUND_MESSAGE = "Unit not found";
    private static final String DISH_PRODUCT_NOT_FOUND_MESSAGE = "Dish product not found";

    @Transactional
    public List<DishProductResponseDto> getDishProductsByDishId(Long dishId) throws EntityNotFoundException {
        if (!dishRepository.existsById(dishId)) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        List<DishProduct> dishProductResponseDtoList = dishProductRepository.findAllByDish_DishId(dishId);

        return dishProductDtoMapper.toDtoList(dishProductResponseDtoList);
    }

    @Transactional
    public List<DishProductResponseDto> getDishProductsByProductId(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        List<DishProduct> dishProductResponseDtoList = dishProductRepository.findAllByProduct_ProductId(productId);

        return dishProductDtoMapper.toDtoList(dishProductResponseDtoList);
    }

    @Transactional
    public DishProductResponseDto createDishProduct(CreateDishProductRequestDto createDishProductRequestDto) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(createDishProductRequestDto.getDishId()).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DISH_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(createDishProductRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Unit unit = unitRepository.findById(createDishProductRequestDto.getUnitId()).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        DishProduct dishProduct = new DishProduct();

        dishProduct.setDish(dish);
        dishProduct.setProduct(product);
        dishProduct.setUnit(unit);
        dishProduct.setUnitCount(createDishProductRequestDto.getUnitCount());

        return dishProductDtoMapper.toDto(dishProductRepository.save(dishProduct));
    }

    @Transactional
    public DishProductResponseDto updateDishProductById(Long dishProductId, UpdateDishProductRequestDto updateDishProductRequestDto) throws EntityNotFoundException {
        DishProduct dishProduct = dishProductRepository.findById(dishProductId).orElse(null);

        if (dishProduct == null) {
            throw new EntityNotFoundException(DISH_PRODUCT_NOT_FOUND_MESSAGE);
        }

        if (updateDishProductRequestDto.getUnitId() != null) {
            Unit unit = unitRepository.findById(updateDishProductRequestDto.getUnitId()).orElse(null);

            if (unit == null) {
                throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
            }

            dishProduct.setUnit(unit);
        }

        if (updateDishProductRequestDto.getUnitCount() != null) {
            dishProduct.setUnitCount(updateDishProductRequestDto.getUnitCount());
        }

        return dishProductDtoMapper.toDto(dishProductRepository.save(dishProduct));
    }

    @Transactional
    public void deleteDishProductById(Long dishProductId) throws EntityNotFoundException {
        if (!dishProductRepository.existsById(dishProductId)) {
            throw new EntityNotFoundException(DISH_PRODUCT_NOT_FOUND_MESSAGE);
        }

        dishProductRepository.deleteById(dishProductId);
    }

}
