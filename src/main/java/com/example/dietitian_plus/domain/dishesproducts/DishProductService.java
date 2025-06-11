package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.common.Messages;
import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.dish.DishRepository;
import com.example.dietitian_plus.domain.dishesproducts.dto.CreateDishProductEntryRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.DishProductDtoMapper;
import com.example.dietitian_plus.domain.dishesproducts.dto.DishProductResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.UpdateDishProductEntryRequestDto;
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

    @Transactional
    public List<DishProductResponseDto> getDishAllAssignedProducts(Long dishId) throws EntityNotFoundException {
        if (!dishRepository.existsById(dishId)) {
            throw new EntityNotFoundException(Messages.DISH_NOT_FOUND);
        }

        List<DishProduct> dishProductResponseDtoList = dishProductRepository.findAllByDish_DishId(dishId);

        return dishProductDtoMapper.toDtoList(dishProductResponseDtoList);
    }

    @Transactional
    public List<DishProductResponseDto> getAllDishProductEntriesWithGivenProduct(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }

        List<DishProduct> dishProductResponseDtoList = dishProductRepository.findAllByProduct_ProductId(productId);

        return dishProductDtoMapper.toDtoList(dishProductResponseDtoList);
    }

    @Transactional
    public DishProductResponseDto createDishProductEntry(CreateDishProductEntryRequestDto createDishProductEntryRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Dish dish = dishRepository.findById(createDishProductEntryRequestDto.getDishId()).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(Messages.DISH_NOT_FOUND);
        }

        Product product = productRepository.findById(createDishProductEntryRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }

        Unit unit = unitRepository.findById(createDishProductEntryRequestDto.getUnitId()).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(Messages.UNIT_NOT_FOUND);
        }

        if (createDishProductEntryRequestDto.getUnitCount() <= 0) {
            throw new IllegalArgumentException(Messages.UNIT_COUNT_MUST_BE_POSITIVE);
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
            throw new EntityNotFoundException(Messages.DISH_PRODUCT_NOT_FOUND);
        }

        if (updateDishProductEntryRequestDto.getUnitId() != null) {
            Unit unit = unitRepository.findById(updateDishProductEntryRequestDto.getUnitId()).orElse(null);

            if (unit == null) {
                throw new EntityNotFoundException(Messages.UNIT_NOT_FOUND);
            }

            dishProduct.setUnit(unit);
        }

        if (updateDishProductEntryRequestDto.getUnitCount() != null) {
            if (updateDishProductEntryRequestDto.getUnitCount() <= 0) {
                throw new IllegalArgumentException(Messages.UNIT_COUNT_MUST_BE_POSITIVE);
            }

            dishProduct.setUnitCount(updateDishProductEntryRequestDto.getUnitCount());
        }

        return dishProductDtoMapper.toDto(dishProductRepository.save(dishProduct));
    }

    @Transactional
    public void deleteDishProductEntryById(Long dishProductId) throws EntityNotFoundException {
        if (!dishProductRepository.existsById(dishProductId)) {
            throw new EntityNotFoundException(Messages.DISH_PRODUCT_NOT_FOUND);
        }

        dishProductRepository.deleteById(dishProductId);
    }

}
