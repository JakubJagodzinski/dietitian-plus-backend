package com.example.dietitian_plus.domain.patientdislikedproducts;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.AssignDislikedProductToPatientRequestDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.PatientDislikedProductResponseDto;
import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
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
public class PatientDislikedProductController {

    private final PatientDislikedProductService patientDislikedProductService;

    @CheckPermission(Permission.PATIENT_DISLIKED_PRODUCT_READ_ALL)
    @GetMapping("/patients/{patientId}/disliked-products")
    public ResponseEntity<List<ProductResponseDto>> getPatientAllDislikedProducts(@PathVariable Long patientId) {
        List<ProductResponseDto> productResponseDtoList = patientDislikedProductService.getPatientAllDislikedProducts(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @CheckPermission(Permission.PATIENT_DISLIKED_PRODUCT_ASSIGN)
    @PostMapping("/patients/{patientId}/disliked-products")
    public ResponseEntity<PatientDislikedProductResponseDto> assignDislikedProductToPatient(@PathVariable Long patientId, @RequestBody AssignDislikedProductToPatientRequestDto assignDislikedProductToPatientRequestDto) {
        PatientDislikedProductResponseDto createdPatientDislikedProductResponseDto = patientDislikedProductService.assignDislikedProductToPatient(patientId, assignDislikedProductToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patients/" + createdPatientDislikedProductResponseDto.getPatientId() + "/disliked-products/" + createdPatientDislikedProductResponseDto.getProductId()))
                .body(createdPatientDislikedProductResponseDto);
    }

    @CheckPermission(Permission.PATIENT_DISLIKED_PRODUCT_UNASSIGN)
    @DeleteMapping("/patients/{patientId}/disliked-products/{productId}")
    public ResponseEntity<MessageResponseDto> unassignDislikedProductFromPatient(@PathVariable Long patientId, @PathVariable Long productId) {
        patientDislikedProductService.unassignDislikedProductFromPatient(patientId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Disliked product with id " + productId + " successfully unassigned from patient with id " + patientId));
    }

}
