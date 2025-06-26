package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.dietitian.dto.request.UpdateDietitianRequestDto;
import com.example.dietitian_plus.domain.dietitian.dto.response.DietitianResponseDto;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class DietitianController {

    private final DietitianService dietitianService;

    @Operation(summary = "Get all dietitians")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all dietitians",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DietitianResponseDto.class)
                    )
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
    @CheckPermission(Permission.DIETITIAN_READ_ALL)
    @GetMapping("/dietitians")
    public ResponseEntity<List<DietitianResponseDto>> getAllDietitians() {
        List<DietitianResponseDto> dietitianResponseDtoList = dietitianService.getAllDietitians();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDtoList);
    }

    @Operation(summary = "Get dietitian by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dietitian found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DietitianResponseDto.class)
                    )
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
                    description = "Dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DIETITIAN_READ)
    @GetMapping("/dietitians/{dietitianId}")
    public ResponseEntity<DietitianResponseDto> getDietitianById(@PathVariable UUID dietitianId) {
        DietitianResponseDto dietitianResponseDto = dietitianService.getDietitianById(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @Operation(summary = "Update dietitian by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dietitian updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DietitianResponseDto.class)
                    )
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
                    description = "Dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DIETITIAN_UPDATE)
    @PatchMapping("/dietitians/{dietitianId}")
    public ResponseEntity<DietitianResponseDto> updateDietitianById(@PathVariable UUID dietitianId, @Valid @RequestBody UpdateDietitianRequestDto updateDietitianRequestDto) {
        DietitianResponseDto dietitianResponseDto = dietitianService.updateDietitianById(dietitianId, updateDietitianRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @Operation(summary = "Delete dietitian by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Dietitian deleted successfully"
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
                    description = "Dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DIETITIAN_DELETE)
    @DeleteMapping("/dietitians/{dietitianId}")
    public ResponseEntity<Void> deleteDietitianById(@PathVariable UUID dietitianId) {
        dietitianService.deleteDietitianById(dietitianId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
