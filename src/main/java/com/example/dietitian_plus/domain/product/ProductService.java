package com.example.dietitian_plus.domain.product;

import com.example.dietitian_plus.common.Messages;
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
            throw new EntityNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }

        return productDtoMapper.toDto(product);
    }

    @Transactional
    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) {
        Product product = new Product();

        product.setProductName(createProductRequestDto.getProductName());

        if (createProductRequestDto.getKcal() != null) {
            product.setKcal(createProductRequestDto.getKcal());
        }

        if (createProductRequestDto.getFats() != null) {
            product.setFats(createProductRequestDto.getFats());
        }

        if (createProductRequestDto.getCarbs() != null) {
            product.setCarbs(createProductRequestDto.getCarbs());
        }

        if (createProductRequestDto.getProtein() != null) {
            product.setProtein(createProductRequestDto.getProtein());
        }

        if (createProductRequestDto.getFiber() != null) {
            product.setFiber(createProductRequestDto.getFiber());
        }

        if (createProductRequestDto.getGlycemicIndex() != null) {
            product.setGlycemicIndex(createProductRequestDto.getGlycemicIndex());
        }

        if (createProductRequestDto.getGlycemicLoad() != null) {
            product.setGlycemicLoad(createProductRequestDto.getGlycemicLoad());
        }

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDto updateProductById(Long productId, UpdateProductRequestDto updateProductRequestDto) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }

        if (updateProductRequestDto.getProductName() != null) {
            product.setProductName(updateProductRequestDto.getProductName());
        }

        if (updateProductRequestDto.getKcal() != null) {
            product.setKcal(updateProductRequestDto.getKcal());
        }

        if (updateProductRequestDto.getFats() != null) {
            product.setFats(updateProductRequestDto.getFats());
        }

        if (updateProductRequestDto.getCarbs() != null) {
            product.setCarbs(updateProductRequestDto.getCarbs());
        }

        if (updateProductRequestDto.getProtein() != null) {
            product.setProtein(updateProductRequestDto.getProtein());
        }

        if (updateProductRequestDto.getFiber() != null) {
            product.setFiber(updateProductRequestDto.getFiber());
        }

        if (updateProductRequestDto.getGlycemicIndex() != null) {
            product.setGlycemicIndex(updateProductRequestDto.getGlycemicIndex());
        }

        if (updateProductRequestDto.getGlycemicLoad() != null) {
            product.setGlycemicLoad(updateProductRequestDto.getGlycemicLoad());
        }

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProductById(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(productId);
    }

}
