package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.auth.access.manager.DishProductAccessManager;
import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.example.dietitian_plus.common.constants.messages.ProductMessages;
import com.example.dietitian_plus.common.constants.messages.UnitMessages;
import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.dish.DishRepository;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dishesproducts.dto.DishProductDtoMapper;
import com.example.dietitian_plus.domain.dishesproducts.dto.request.AddProductToDishRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.request.UpdateDishProductRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishProductResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishWithProductsResponseDto;
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
    public DishProductResponseDto addProductToDish(Long dishId, AddProductToDishRequestDto addProductToDishRequestDto) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        dishProductAccessManager.checkCanAddProductToDish(dish);

        Product product = productRepository.findById(addProductToDishRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        Unit unit = unitRepository.findById(addProductToDishRequestDto.getUnitId()).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(UnitMessages.UNIT_NOT_FOUND);
        }

        DishProduct dishProduct = new DishProduct();

        dishProduct.setDish(dish);
        dishProduct.setProduct(product);
        dishProduct.setUnit(unit);
        dishProduct.setUnitCount(addProductToDishRequestDto.getUnitCount());

        return dishProductDtoMapper.toDto(dishProductRepository.save(dishProduct));
    }

    @Transactional
    public void addManyProductsToDish(Long dishId, List<AddProductToDishRequestDto> addProductToDishRequestDtoList) throws EntityNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElse(null);

        if (dish == null) {
            throw new EntityNotFoundException(DishMessages.DISH_NOT_FOUND);
        }

        if (addProductToDishRequestDtoList == null || addProductToDishRequestDtoList.isEmpty()) {
            return;
        }

        dishProductAccessManager.checkCanAddProductToDish(dish);

        for (AddProductToDishRequestDto addProductToDishRequestDto : addProductToDishRequestDtoList) {
            Product product = productRepository.findById(addProductToDishRequestDto.getProductId()).orElse(null);

            if (product == null) {
                throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
            }

            Unit unit = unitRepository.findById(addProductToDishRequestDto.getUnitId()).orElse(null);

            if (unit == null) {
                throw new EntityNotFoundException(UnitMessages.UNIT_NOT_FOUND);
            }

            DishProduct dishProduct = new DishProduct();

            dishProduct.setDish(dish);
            dishProduct.setProduct(product);
            dishProduct.setUnit(unit);
            dishProduct.setUnitCount(addProductToDishRequestDto.getUnitCount());

            dishProductRepository.save(dishProduct);
        }
    }

    @Transactional
    public DishProductResponseDto updateDishProductById(Long dishProductId, UpdateDishProductRequestDto updateDishProductRequestDto) throws EntityNotFoundException {
        DishProduct dishProduct = dishProductRepository.findById(dishProductId).orElse(null);

        if (dishProduct == null) {
            throw new EntityNotFoundException(DishMessages.DISH_PRODUCT_NOT_FOUND);
        }

        dishProductAccessManager.checkCanUpdateDishProductEntry(dishProduct);

        if (updateDishProductRequestDto.getUnitId() != null) {
            Unit unit = unitRepository.findById(updateDishProductRequestDto.getUnitId()).orElse(null);

            if (unit == null) {
                throw new EntityNotFoundException(UnitMessages.UNIT_NOT_FOUND);
            }

            dishProduct.setUnit(unit);
        }

        if (updateDishProductRequestDto.getUnitCount() != null) {
            dishProduct.setUnitCount(updateDishProductRequestDto.getUnitCount());
        }

        return dishProductDtoMapper.toDto(dishProductRepository.save(dishProduct));
    }

    @Transactional
    public void removeProductFromDish(Long dishProductId) throws EntityNotFoundException {
        DishProduct dishProduct = dishProductRepository.findById(dishProductId).orElse(null);

        if (dishProduct == null) {
            throw new EntityNotFoundException(DishMessages.DISH_PRODUCT_NOT_FOUND);
        }

        dishProductAccessManager.checkCanDeleteDishProductEntry(dishProduct);

        dishProductRepository.deleteById(dishProductId);
    }

}
