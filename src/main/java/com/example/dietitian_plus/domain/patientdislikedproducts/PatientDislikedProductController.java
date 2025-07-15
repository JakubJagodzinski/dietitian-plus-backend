package com.example.dietitian_plus.domain.patientdislikedproducts;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.dto.ApiErrorResponseDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.request.AssignDislikedProductToPatientRequestDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.response.PatientDislikedProductResponseDto;
import com.example.dietitian_plus.domain.product.dto.response.ProductResponseDto;
import com.example.dietitian_plus.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
public class PatientDislikedProductController {

    private final PatientDislikedProductService patientDislikedProductService;

    @Operation(
            summary = "Read patient all disliked products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of patient all disliked products",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class))
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_DISLIKED_PRODUCT_READ_ALL)
    @GetMapping("/patients/{patientId}/disliked-products")
    public ResponseEntity<List<ProductResponseDto>> getPatientAllDislikedProducts(@PathVariable UUID patientId) {
        List<ProductResponseDto> productResponseDtoList = patientDislikedProductService.getPatientAllDislikedProducts(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @Operation(
            summary = "Assign disliked product to patient"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Disliked product assigned to patient successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatientDislikedProductResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Disliked product already assigned to patient",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient / product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_DISLIKED_PRODUCT_ASSIGN)
    @PostMapping("/patients/{patientId}/disliked-products")
    public ResponseEntity<PatientDislikedProductResponseDto> assignDislikedProductToPatient(@PathVariable UUID patientId, @Valid @RequestBody AssignDislikedProductToPatientRequestDto assignDislikedProductToPatientRequestDto) {
        PatientDislikedProductResponseDto createdPatientDislikedProductResponseDto = patientDislikedProductService.assignDislikedProductToPatient(patientId, assignDislikedProductToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patients/" + createdPatientDislikedProductResponseDto.getPatientId() + "/disliked-products/" + createdPatientDislikedProductResponseDto.getProductId()))
                .body(createdPatientDislikedProductResponseDto);
    }

    @Operation(
            summary = "Unassign disliked product from patient"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Disliked product unassigned from patient successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Disliked product not assigned to patient",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient / product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_DISLIKED_PRODUCT_UNASSIGN)
    @DeleteMapping("/patients/{patientId}/disliked-products/{productId}")
    public ResponseEntity<Void> unassignDislikedProductFromPatient(@PathVariable UUID patientId, @PathVariable Long productId) {
        patientDislikedProductService.unassignDislikedProductFromPatient(patientId, productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
