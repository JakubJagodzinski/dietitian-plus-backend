package com.example.dietitian_plus.domain.disease;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.disease.dto.request.CreateDiseaseRequestDto;
import com.example.dietitian_plus.domain.disease.dto.request.UpdateDiseaseRequestDto;
import com.example.dietitian_plus.domain.disease.dto.response.DiseaseResponseDto;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class DiseaseController {

    private final DiseaseService diseaseService;

    @Operation(
            summary = "Get all diseases"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all diseases",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiseaseResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            )
    })
    @CheckPermission(Permission.DISEASE_READ_ALL)
    @GetMapping("/diseases")
    public ResponseEntity<List<DiseaseResponseDto>> getAllDiseases() {
        List<DiseaseResponseDto> diseaseResponseDtoList = diseaseService.getAllDiseases();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDtoList);
    }

    @Operation(
            summary = "Get disease by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Disease found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiseaseResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Disease not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DISEASE_READ)
    @GetMapping("/diseases/{diseaseId}")
    public ResponseEntity<DiseaseResponseDto> getDiseaseById(@PathVariable Long diseaseId) {
        DiseaseResponseDto diseaseResponseDto = diseaseService.getDiseaseById(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDto);
    }

    @Operation(
            summary = "Create new disease"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Disease created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiseaseResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Disease with that name already exists",
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
            )
    })
    @CheckPermission(Permission.DISEASE_CREATE)
    @PostMapping("/diseases")
    public ResponseEntity<DiseaseResponseDto> createDisease(@Valid @RequestBody CreateDiseaseRequestDto createDiseaseRequestDto) {
        DiseaseResponseDto createdDiseaseResponseDto = diseaseService.createDisease(createDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/diseases/" + createdDiseaseResponseDto.getDiseaseId()))
                .body(createdDiseaseResponseDto);
    }

    @Operation(
            summary = "Update disease by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Disease updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiseaseResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Disease with that name already exists",
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
                    description = "Disease not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DISEASE_UPDATE)
    @PatchMapping("/diseases/{diseaseId}")
    public ResponseEntity<DiseaseResponseDto> updateDiseaseById(@PathVariable Long diseaseId, @Valid @RequestBody UpdateDiseaseRequestDto updateDiseaseRequestDto) {
        DiseaseResponseDto updatedDiseaseResponseDto = diseaseService.updateDiseaseById(diseaseId, updateDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDiseaseResponseDto);
    }

    @Operation(
            summary = "Delete disease by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Disease deleted successfully"
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
                    description = "Disease not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DISEASE_DELETE)
    @DeleteMapping("/diseases/{diseaseId}")
    public ResponseEntity<Void> deleteDiseaseById(@PathVariable Long diseaseId) {
        diseaseService.deleteDiseaseById(diseaseId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
