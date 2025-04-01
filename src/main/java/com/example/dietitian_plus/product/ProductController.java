package com.example.dietitian_plus.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductDto createProductDto) {
        ProductDto createdProductDto = productService.createProduct(createProductDto);
        return ResponseEntity
                .created(URI.create("/api/products/" + createdProductDto.getProductId()))
                .body(createdProductDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id, @RequestBody CreateProductDto createProductDto) {
        return ResponseEntity.ok(productService.updateProductById(id, createProductDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

}
