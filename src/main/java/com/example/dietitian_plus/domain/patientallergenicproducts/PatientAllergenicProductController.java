package com.example.dietitian_plus.domain.patientallergenicproducts;

import com.example.dietitian_plus.auth.access.annotation.OwnerPatientAccess;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.AssignAllergenicProductToPatientRequestDto;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.PatientAllergenicProductResponseDto;
import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PatientAllergenicProductController {

    private final PatientAllergenicProductService patientAllergenicProductService;

    // TODO check ownership in access manager
    @GetMapping("/patients/{patientId}/allergenic-products")
    public ResponseEntity<List<ProductResponseDto>> getPatientAllAllergenicProducts(@PathVariable Long patientId) {
        List<ProductResponseDto> productResponseDtoList = patientAllergenicProductService.getPatientAllAllergenicProducts(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @OwnerPatientAccess
    @PostMapping("/patients/{patientId}/allergenic-products")
    public ResponseEntity<PatientAllergenicProductResponseDto> assignAllergenicProductToPatient(@PathVariable Long patientId, @RequestBody AssignAllergenicProductToPatientRequestDto assignAllergenicProductToPatientRequestDto) {
        PatientAllergenicProductResponseDto createdPatientAllergenicProductResponseDto = patientAllergenicProductService.assignAllergenicProductToPatient(patientId, assignAllergenicProductToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patients/" + createdPatientAllergenicProductResponseDto.getPatientId() + "/allergenic-products/" + createdPatientAllergenicProductResponseDto.getProductId()))
                .body(createdPatientAllergenicProductResponseDto);
    }

    @OwnerPatientAccess
    @DeleteMapping("/patients/{patientId}/allergenic-products/{productId}")
    public ResponseEntity<MessageResponseDto> unassignAllergenicProductFromPatient(@PathVariable Long patientId, @PathVariable Long productId) {
        patientAllergenicProductService.unassignAllergenicProductFromPatient(patientId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Allergenic product with id " + productId + " successfully unassigned from patient with id " + patientId));
    }

}
