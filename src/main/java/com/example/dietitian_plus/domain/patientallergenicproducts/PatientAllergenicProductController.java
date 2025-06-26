package com.example.dietitian_plus.domain.patientallergenicproducts;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.request.AssignAllergenicProductToPatientRequestDto;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.response.PatientAllergenicProductResponseDto;
import com.example.dietitian_plus.domain.product.dto.response.ProductResponseDto;
import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PatientAllergenicProductController {

    private final PatientAllergenicProductService patientAllergenicProductService;

    @CheckPermission(Permission.PATIENT_ALLERGENIC_PRODUCT_READ_ALL)
    @GetMapping("/patients/{patientId}/allergenic-products")
    public ResponseEntity<List<ProductResponseDto>> getPatientAllAllergenicProducts(@PathVariable UUID patientId) {
        List<ProductResponseDto> productResponseDtoList = patientAllergenicProductService.getPatientAllAllergenicProducts(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @CheckPermission(Permission.PATIENT_ALLERGENIC_PRODUCT_ASSIGN)
    @PostMapping("/patients/{patientId}/allergenic-products")
    public ResponseEntity<PatientAllergenicProductResponseDto> assignAllergenicProductToPatient(@PathVariable UUID patientId, @RequestBody AssignAllergenicProductToPatientRequestDto assignAllergenicProductToPatientRequestDto) {
        PatientAllergenicProductResponseDto createdPatientAllergenicProductResponseDto = patientAllergenicProductService.assignAllergenicProductToPatient(patientId, assignAllergenicProductToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patients/" + createdPatientAllergenicProductResponseDto.getPatientId() + "/allergenic-products/" + createdPatientAllergenicProductResponseDto.getProductId()))
                .body(createdPatientAllergenicProductResponseDto);
    }

    @CheckPermission(Permission.PATIENT_ALLERGENIC_PRODUCT_UNASSIGN)
    @DeleteMapping("/patients/{patientId}/allergenic-products/{productId}")
    public ResponseEntity<Void> unassignAllergenicProductFromPatient(@PathVariable UUID patientId, @PathVariable Long productId) {
        patientAllergenicProductService.unassignAllergenicProductFromPatient(patientId, productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
