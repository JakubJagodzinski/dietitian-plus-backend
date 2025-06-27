package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.unit.dto.request.CreateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.request.UpdateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.response.UnitResponseDto;
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
public class UnitController {

    private final UnitService unitService;

    @Operation(
            summary = "Get all units"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all units",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnitResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            )
    })
    @CheckPermission(Permission.UNIT_READ_ALL)
    @GetMapping("/units")
    public ResponseEntity<List<UnitResponseDto>> getAllUnits() {
        List<UnitResponseDto> unitResponseDtoList = unitService.getAllUnits();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(unitResponseDtoList);
    }

    @Operation(
            summary = "Get unit by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Unit found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnitResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Unit not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.UNIT_READ)
    @GetMapping("/units/{unitId}")
    public ResponseEntity<UnitResponseDto> getUnitById(@PathVariable Long unitId) {
        UnitResponseDto unitResponseDto = unitService.getUnitById(unitId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(unitResponseDto);
    }

    @Operation(
            summary = "Create new unit"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Unit created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnitResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unit with that name already exists",
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
    @CheckPermission(Permission.UNIT_CREATE)
    @PostMapping("/units")
    public ResponseEntity<UnitResponseDto> createUnit(@Valid @RequestBody CreateUnitRequestDto createUnitRequestDto) {
        UnitResponseDto createdUnitResponseDto = unitService.createUnit(createUnitRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/units/" + createdUnitResponseDto.getUnitId()))
                .body(createdUnitResponseDto);
    }

    @Operation(
            summary = "Update unit by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Unit updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnitResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unit with that name already exists",
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
                    description = "Unit not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.UNIT_UPDATE)
    @PatchMapping("/units/{unitId}")
    public ResponseEntity<UnitResponseDto> updateUnitById(@PathVariable Long unitId, @Valid @RequestBody UpdateUnitRequestDto updateUnitRequestDto) {
        UnitResponseDto updatedUnitResponseDto = unitService.updateUnitById(unitId, updateUnitRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedUnitResponseDto);
    }

    @Operation(
            summary = "Delete unit by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Unit deleted successfully"
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
                    description = "Unit not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.UNIT_DELETE)
    @DeleteMapping("/units/{unitId}")
    public ResponseEntity<Void> deleteUnitById(@PathVariable Long unitId) {
        unitService.deleteUnitById(unitId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
