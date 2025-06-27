package com.example.dietitian_plus.domain.patientallergenicproducts;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.request.AssignAllergenicProductToPatientRequestDto;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.response.PatientAllergenicProductResponseDto;
import com.example.dietitian_plus.domain.product.dto.response.ProductResponseDto;
import com.example.dietitian_plus.exception.ApiError;
import com.example.dietitian_plus.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class PatientAllergenicProductController {

    private final PatientAllergenicProductService patientAllergenicProductService;

    @Operation(
            summary = "Read patient all allergenic products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of patient all allergenic products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_ALLERGENIC_PRODUCT_READ_ALL)
    @GetMapping("/patients/{patientId}/allergenic-products")
    public ResponseEntity<List<ProductResponseDto>> getPatientAllAllergenicProducts(@PathVariable UUID patientId) {
        List<ProductResponseDto> productResponseDtoList = patientAllergenicProductService.getPatientAllAllergenicProducts(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @Operation(
            summary = "Assign allergenic product to patient"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Allergenic product assigned to patient successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatientAllergenicProductResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Allergenic product already assigned to patient",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient / product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_ALLERGENIC_PRODUCT_ASSIGN)
    @PostMapping("/patients/{patientId}/allergenic-products")
    public ResponseEntity<PatientAllergenicProductResponseDto> assignAllergenicProductToPatient(@PathVariable UUID patientId, @Valid @RequestBody AssignAllergenicProductToPatientRequestDto assignAllergenicProductToPatientRequestDto) {
        PatientAllergenicProductResponseDto createdPatientAllergenicProductResponseDto = patientAllergenicProductService.assignAllergenicProductToPatient(patientId, assignAllergenicProductToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patients/" + createdPatientAllergenicProductResponseDto.getPatientId() + "/allergenic-products/" + createdPatientAllergenicProductResponseDto.getProductId()))
                .body(createdPatientAllergenicProductResponseDto);
    }

    @Operation(
            summary = "Unassign allergenic product from patient"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Allergenic product unassigned from patient successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Allergenic product not assigned to patient",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient / product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_ALLERGENIC_PRODUCT_UNASSIGN)
    @DeleteMapping("/patients/{patientId}/allergenic-products/{productId}")
    public ResponseEntity<Void> unassignAllergenicProductFromPatient(@PathVariable UUID patientId, @PathVariable Long productId) {
        patientAllergenicProductService.unassignAllergenicProductFromPatient(patientId, productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
