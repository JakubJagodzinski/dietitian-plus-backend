package com.example.dietitian_plus.domain.product;

import com.example.dietitian_plus.common.constants.messages.ProductMessages;
import com.example.dietitian_plus.domain.product.dto.CreateProductRequestDto;
import com.example.dietitian_plus.domain.product.dto.ProductDtoMapper;
import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
import com.example.dietitian_plus.domain.product.dto.UpdateProductRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtoMapper productDtoMapper;

    public List<ProductResponseDto> getAllProducts() {
        return productDtoMapper.toDtoList(productRepository.findAll());
    }

    @Transactional
    public ProductResponseDto getProductById(Long productId) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        return productDtoMapper.toDto(product);
    }

    @Transactional
    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) throws IllegalArgumentException {
        Product product = new Product();

        product.setProductName(createProductRequestDto.getProductName());

        if (createProductRequestDto.getKcal() != null) {
            if (createProductRequestDto.getKcal() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setKcal(createProductRequestDto.getKcal());
        }

        if (createProductRequestDto.getFats() != null) {
            if (createProductRequestDto.getFats() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setFats(createProductRequestDto.getFats());
        }

        if (createProductRequestDto.getCarbs() != null) {
            if (createProductRequestDto.getCarbs() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setCarbs(createProductRequestDto.getCarbs());
        }

        if (createProductRequestDto.getProtein() != null) {
            if (createProductRequestDto.getProtein() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setProtein(createProductRequestDto.getProtein());
        }

        if (createProductRequestDto.getFiber() != null) {
            if (createProductRequestDto.getFiber() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setFiber(createProductRequestDto.getFiber());
        }

        if (createProductRequestDto.getGlycemicIndex() != null) {
            if (createProductRequestDto.getGlycemicIndex() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setGlycemicIndex(createProductRequestDto.getGlycemicIndex());
        }

        if (createProductRequestDto.getGlycemicLoad() != null) {
            if (createProductRequestDto.getGlycemicLoad() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setGlycemicLoad(createProductRequestDto.getGlycemicLoad());
        }

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDto updateProductById(Long productId, UpdateProductRequestDto updateProductRequestDto) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        if (updateProductRequestDto.getProductName() != null) {
            if (updateProductRequestDto.getProductName().isEmpty()) {
                throw new IllegalArgumentException(ProductMessages.PRODUCT_NAME_CANNOT_BE_EMPTY);
            }

            product.setProductName(updateProductRequestDto.getProductName());
        }

        if (updateProductRequestDto.getKcal() != null) {
            if (updateProductRequestDto.getKcal() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setKcal(updateProductRequestDto.getKcal());
        }

        if (updateProductRequestDto.getFats() != null) {
            if (updateProductRequestDto.getFats() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setFats(updateProductRequestDto.getFats());
        }

        if (updateProductRequestDto.getCarbs() != null) {
            if (updateProductRequestDto.getCarbs() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setCarbs(updateProductRequestDto.getCarbs());
        }

        if (updateProductRequestDto.getProtein() != null) {
            if (updateProductRequestDto.getProtein() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setProtein(updateProductRequestDto.getProtein());
        }

        if (updateProductRequestDto.getFiber() != null) {
            if (updateProductRequestDto.getFiber() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setFiber(updateProductRequestDto.getFiber());
        }

        if (updateProductRequestDto.getGlycemicIndex() != null) {
            if (updateProductRequestDto.getGlycemicIndex() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setGlycemicIndex(updateProductRequestDto.getGlycemicIndex());
        }

        if (updateProductRequestDto.getGlycemicLoad() != null) {
            if (updateProductRequestDto.getGlycemicLoad() < 0) {
                throw new IllegalArgumentException(ProductMessages.NUTRITIONAL_VALUES_CANNOT_BE_NEGATIVE);
            }

            product.setGlycemicLoad(updateProductRequestDto.getGlycemicLoad());
        }

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProductById(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(productId);
    }

}
