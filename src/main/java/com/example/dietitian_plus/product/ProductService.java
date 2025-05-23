package com.example.dietitian_plus.product;

import com.example.dietitian_plus.product.dto.CreateProductRequestDto;
import com.example.dietitian_plus.product.dto.ProductResponseDto;
import com.example.dietitian_plus.product.dto.ProductDtoMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtoMapper productDtoMapper;

    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";

    @Autowired
    public ProductService(ProductRepository productRepository, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
    }

    public List<ProductResponseDto> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productsDto = new ArrayList<>();

        for (Product product : products) {
            productsDto.add(productDtoMapper.toDto(product));
        }

        return productsDto;
    }

    public ProductResponseDto getProductById(Long id) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        return productDtoMapper.toDto(productRepository.getReferenceById(id));
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

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDto updateProductById(Long id, CreateProductRequestDto updateProductDto) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.getReferenceById(id);

        if (updateProductDto.getProductName() != null) {
            product.setProductName(updateProductDto.getProductName());
        }
        if (updateProductDto.getKcal() != null) {
            product.setKcal(updateProductDto.getKcal());
        }
        if (updateProductDto.getFats() != null) {
            product.setFats(updateProductDto.getFats());
        }
        if (updateProductDto.getCarbs() != null) {
            product.setCarbs(updateProductDto.getCarbs());
        }
        if (updateProductDto.getProtein() != null) {
            product.setProtein(updateProductDto.getProtein());
        }
        if (updateProductDto.getFiber() != null) {
            product.setFiber(updateProductDto.getFiber());
        }

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProductById(Long id) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        productRepository.deleteById(id);
    }

}
