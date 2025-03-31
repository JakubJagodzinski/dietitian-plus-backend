package com.example.dietitian_plus.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productsDto = new ArrayList<>();

        for (Product product : products) {
            productsDto.add(mapToDto(product));
        }

        return productsDto;
    }

    public ProductDto getProductById(Long id) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        return mapToDto(productRepository.getReferenceById(id));
    }

    @Transactional
    public ProductDto createProduct(CreateProductDto createProductDto) {
        Product product = new Product();

        product.setProductName(createProductDto.getProductName());
        product.setKcal(createProductDto.getKcal());
        product.setFats(createProductDto.getFats());
        product.setCarbs(createProductDto.getCarbs());
        product.setProtein(createProductDto.getProtein());

        return mapToDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto updateProductById(Long id, CreateProductDto updateProductDto) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.getReferenceById(id);

        if (product.getProductName() != null) {
            product.setProductName(updateProductDto.getProductName());
        }
        if (product.getKcal() != null) {
            product.setKcal(updateProductDto.getKcal());
        }
        if (product.getFats() != null) {
            product.setFats(updateProductDto.getFats());
        }
        if (product.getCarbs() != null) {
            product.setCarbs(updateProductDto.getCarbs());
        }
        if (product.getProtein() != null) {
            product.setProtein(updateProductDto.getProtein());
        }

        return mapToDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProductById(Long id) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        productRepository.deleteById(id);
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();

        productDto.setProductName(product.getProductName());
        productDto.setKcal(product.getKcal());
        productDto.setFats(product.getFats());
        productDto.setCarbs(product.getCarbs());
        productDto.setProtein(product.getProtein());
        productDto.setFiber(product.getFiber());

        return productDto;
    }

}
