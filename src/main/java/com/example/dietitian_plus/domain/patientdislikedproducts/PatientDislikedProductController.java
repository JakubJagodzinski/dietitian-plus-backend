package com.example.dietitian_plus.domain.patientdislikedproducts;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.request.AssignDislikedProductToPatientRequestDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.response.PatientDislikedProductResponseDto;
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
public class PatientDislikedProductController {

    private final PatientDislikedProductService patientDislikedProductService;

    @CheckPermission(Permission.PATIENT_DISLIKED_PRODUCT_READ_ALL)
    @GetMapping("/patients/{patientId}/disliked-products")
    public ResponseEntity<List<ProductResponseDto>> getPatientAllDislikedProducts(@PathVariable UUID patientId) {
        List<ProductResponseDto> productResponseDtoList = patientDislikedProductService.getPatientAllDislikedProducts(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @CheckPermission(Permission.PATIENT_DISLIKED_PRODUCT_ASSIGN)
    @PostMapping("/patients/{patientId}/disliked-products")
    public ResponseEntity<PatientDislikedProductResponseDto> assignDislikedProductToPatient(@PathVariable UUID patientId, @RequestBody AssignDislikedProductToPatientRequestDto assignDislikedProductToPatientRequestDto) {
        PatientDislikedProductResponseDto createdPatientDislikedProductResponseDto = patientDislikedProductService.assignDislikedProductToPatient(patientId, assignDislikedProductToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patients/" + createdPatientDislikedProductResponseDto.getPatientId() + "/disliked-products/" + createdPatientDislikedProductResponseDto.getProductId()))
                .body(createdPatientDislikedProductResponseDto);
    }

    @CheckPermission(Permission.PATIENT_DISLIKED_PRODUCT_UNASSIGN)
    @DeleteMapping("/patients/{patientId}/disliked-products/{productId}")
    public ResponseEntity<MessageResponseDto> unassignDislikedProductFromPatient(@PathVariable UUID patientId, @PathVariable Long productId) {
        patientDislikedProductService.unassignDislikedProductFromPatient(patientId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Disliked product with id " + productId + " successfully unassigned from patient with id " + patientId));
    }

}
