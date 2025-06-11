package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.auth.access.annotation.AdminOnly;
import com.example.dietitian_plus.auth.access.annotation.DietitianAccess;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.CreateDishProductEntryRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.DishProductResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.UpdateDishProductEntryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DishProductController {

    private final DishProductService dishProductService;

    // TODO check ownership in access manager
    @GetMapping("/dishes/{dishId}/dishes-products")
    public ResponseEntity<List<DishProductResponseDto>> getDishAllAssignedProducts(@PathVariable Long dishId) {
        List<DishProductResponseDto> dishProductResponseDtoList = dishProductService.getDishAllAssignedProducts(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishProductResponseDtoList);
    }

    @AdminOnly
    @GetMapping("/products/{productId}/dishes-products")
    public ResponseEntity<List<DishProductResponseDto>> getAllDishProductEntriesWithGivenProduct(@PathVariable Long productId) {
        List<DishProductResponseDto> dishProductResponseDtoList = dishProductService.getAllDishProductEntriesWithGivenProduct(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishProductResponseDtoList);
    }

    @DietitianAccess
    @PostMapping("/dishes-products")
    public ResponseEntity<DishProductResponseDto> createDishProductEntry(@RequestBody CreateDishProductEntryRequestDto createDishProductEntryRequestDto) {
        DishProductResponseDto createdDishProductResponseDto = dishProductService.createDishProductEntry(createDishProductEntryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes-products/" + createdDishProductResponseDto.getId()))
                .body(createdDishProductResponseDto);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @PutMapping("/dishes-products/{dishProductId}")
    public ResponseEntity<DishProductResponseDto> updateDishProductEntryById(@PathVariable Long dishProductId, @RequestBody UpdateDishProductEntryRequestDto updateDishProductEntryRequestDto) {
        DishProductResponseDto updatedDishProductResponseDto = dishProductService.updateDishProductEntryById(dishProductId, updateDishProductEntryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDishProductResponseDto);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @DeleteMapping("/dishes-products/{dishProductId}")
    public ResponseEntity<MessageResponseDto> deleteDishProductEntryById(@PathVariable Long dishProductId) {
        dishProductService.deleteDishProductEntryById(dishProductId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dish product entry with id " + dishProductId + " deleted successfully"));
    }

}
