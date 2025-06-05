package com.example.dietitian_plus.domain.patientallergenicproducts;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.CreatePatientAllergenicProductRequestDto;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.PatientAllergenicProductResponseDto;
import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient-allergenic-products")
@RequiredArgsConstructor
public class PatientAllergenicProductController {

    private final PatientAllergenicProductService patientAllergenicProductService;

    @GetMapping("/{patientId}")
    public ResponseEntity<List<ProductResponseDto>> getPatientAllergenicProductsByPatientId(@PathVariable Long patientId) {
        List<ProductResponseDto> productResponseDtoList = patientAllergenicProductService.getPatientAllergenicProductsByPatientId(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<PatientAllergenicProductResponseDto> createPatientAllergenicProduct(@RequestBody CreatePatientAllergenicProductRequestDto createPatientAllergenicProductRequestDto) {
        PatientAllergenicProductResponseDto createdPatientAllergenicProductResponseDto = patientAllergenicProductService.createPatientAllergenicProduct(createPatientAllergenicProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patient-allergenic-products/" + createdPatientAllergenicProductResponseDto.getPatientId()))
                .body(createdPatientAllergenicProductResponseDto);
    }

    @DeleteMapping("/{patientId}/{productId}")
    public ResponseEntity<MessageResponseDto> deletePatientAllergenicProductById(@PathVariable Long patientId, @PathVariable Long productId) {
        patientAllergenicProductService.deletePatientAllergenicProductById(patientId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Patient allergenic product with patient id " + patientId + " and product id " + productId + " deleted successfully"));
    }

}
