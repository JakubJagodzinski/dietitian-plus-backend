package com.example.dietitian_plus.domain.patientdislikedproducts;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.CreatePatientDislikedProductRequestDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.PatientDislikedProductResponseDto;
import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient-disliked-products")
@RequiredArgsConstructor
public class PatientDislikedProductController {

    private final PatientDislikedProductService patientDislikedProductService;

    @GetMapping("/{patientId}")
    public ResponseEntity<List<ProductResponseDto>> getPatientDislikedProductsByPatientId(@PathVariable Long patientId) {
        List<ProductResponseDto> productResponseDtoList = patientDislikedProductService.getPatientDislikedProductsByPatientId(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<PatientDislikedProductResponseDto> createPatientDislikedProduct(@RequestBody CreatePatientDislikedProductRequestDto createPatientDislikedProductRequestDto) {
        PatientDislikedProductResponseDto createdPatientDislikedProductResponseDto = patientDislikedProductService.createPatientDislikedProduct(createPatientDislikedProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patient-disliked-products/" + createdPatientDislikedProductResponseDto.getPatientId()))
                .body(createdPatientDislikedProductResponseDto);
    }

    @DeleteMapping("/{patientId}/{productId}")
    public ResponseEntity<MessageResponseDto> deletePatientDislikedProductById(@PathVariable Long patientId, @PathVariable Long productId) {
        patientDislikedProductService.deletePatientDislikedProductById(patientId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Patient disliked product with patient id " + patientId + " and product id " + productId + " deleted successfully"));
    }

}
