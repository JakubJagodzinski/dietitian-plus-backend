package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.auth.access.manager.DishProductAccessManager;
import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.example.dietitian_plus.common.constants.messages.ProductMessages;
import com.example.dietitian_plus.common.constants.messages.UnitMessages;
import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.dish.DishRepository;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dishesproducts.dto.*;
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
    private final DishDtoMapper dishDtoMapper;

    private final DishProductAccessManager dishProductAccessManager;

    @Transactional
    public DishWithProductsResponseDto getDishWithProducts(Long dishId) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        dishProductAccessManager.checkCanReadDishWithProducts(dish);

        List<DishProduct> dishProductList = dishProductRepository.findAllByDish_DishId(dishId);

        DishWithProductsResponseDto dishWithProductsResponseDto = new DishWithProductsResponseDto();

        dishWithProductsResponseDto.setDish(dishDtoMapper.toDto(dish));
        dishWithProductsResponseDto.setProducts(dishProductDtoMapper.toDtoList(dishProductList));

        return dishWithProductsResponseDto;
    }

    @Transactional
    public DishProductResponseDto createDishProductEntry(CreateDishProductEntryRequestDto createDishProductEntryRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Dish dish = dishRepository.findById(createDishProductEntryRequestDto.getDishId()).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        dishProductAccessManager.checkCanCreateDishProductEntry(dish);

        Product product = productRepository.findById(createDishProductEntryRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        Unit unit = unitRepository.findById(createDishProductEntryRequestDto.getUnitId()).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(UnitMessages.UNIT_NOT_FOUND);
        }

        if (createDishProductEntryRequestDto.getUnitCount() <= 0) {
            throw new IllegalArgumentException(UnitMessages.UNIT_COUNT_MUST_BE_POSITIVE);
        }

        DishProduct dishProduct = new DishProduct();

        dishProduct.setDish(dish);
        dishProduct.setProduct(product);
        dishProduct.setUnit(unit);
        dishProduct.setUnitCount(createDishProductEntryRequestDto.getUnitCount());

        return dishProductDtoMapper.toDto(dishProductRepository.save(dishProduct));
    }

    @Transactional
    public DishProductResponseDto updateDishProductEntryById(Long dishProductId, UpdateDishProductEntryRequestDto updateDishProductEntryRequestDto) throws EntityNotFoundException {
        DishProduct dishProduct = dishProductRepository.findById(dishProductId).orElse(null);

        if (dishProduct == null) {
            throw new EntityNotFoundException(DishMessages.DISH_PRODUCT_NOT_FOUND);
        }

        dishProductAccessManager.checkCanUpdateDishProductEntry(dishProduct);

        if (updateDishProductEntryRequestDto.getUnitId() != null) {
            Unit unit = unitRepository.findById(updateDishProductEntryRequestDto.getUnitId()).orElse(null);

            if (unit == null) {
                throw new EntityNotFoundException(UnitMessages.UNIT_NOT_FOUND);
            }

            dishProduct.setUnit(unit);
        }

        if (updateDishProductEntryRequestDto.getUnitCount() != null) {
            if (updateDishProductEntryRequestDto.getUnitCount() <= 0) {
                throw new IllegalArgumentException(UnitMessages.UNIT_COUNT_MUST_BE_POSITIVE);
            }

            dishProduct.setUnitCount(updateDishProductEntryRequestDto.getUnitCount());
        }

        return dishProductDtoMapper.toDto(dishProductRepository.save(dishProduct));
    }

    @Transactional
    public void deleteDishProductEntryById(Long dishProductId) throws EntityNotFoundException {
        DishProduct dishProduct = dishProductRepository.findById(dishProductId).orElse(null);

        if (dishProduct == null) {
            throw new EntityNotFoundException(DishMessages.DISH_PRODUCT_NOT_FOUND);
        }

        dishProductAccessManager.checkCanDeleteDishProductEntry(dishProduct);

        dishProductRepository.deleteById(dishProductId);
    }

}
