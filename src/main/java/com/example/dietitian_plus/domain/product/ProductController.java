package com.example.dietitian_plus.domain.product;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.product.dto.request.CreateProductRequestDto;
import com.example.dietitian_plus.domain.product.dto.request.UpdateProductRequestDto;
import com.example.dietitian_plus.domain.product.dto.response.ProductResponseDto;
import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @CheckPermission(Permission.PRODUCT_READ_ALL)
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productResponseDtoList = productService.getAllProducts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @CheckPermission(Permission.PRODUCT_READ)
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.getProductById(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_CREATE)
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        ProductResponseDto createdProductResponseDto = productService.createProduct(createProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/products/" + createdProductResponseDto.getProductId()))
                .body(createdProductResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_UPDATE)
    @PatchMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> updateProductById(@PathVariable Long productId, @RequestBody UpdateProductRequestDto updateProductRequestDto) {
        ProductResponseDto updatedProductResponseDto = productService.updateProductById(productId, updateProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProductResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_DELETE)
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
