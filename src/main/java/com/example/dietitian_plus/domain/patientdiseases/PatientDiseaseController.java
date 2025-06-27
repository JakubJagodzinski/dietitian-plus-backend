package com.example.dietitian_plus.domain.patientdiseases;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.disease.dto.response.DiseaseResponseDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.request.AssignDiseaseToPatientRequestDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.response.PatientDiseaseResponseDto;
import com.example.dietitian_plus.common.dto.ApiErrorResponseDto;
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
public class PatientDiseaseController {

    private final PatientDiseaseService patientDiseaseService;

    @Operation(
            summary = "Read patient all diseases"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of patient all diseases",
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
    @CheckPermission(Permission.PATIENT_DISEASE_READ_ALL)
    @GetMapping("/patients/{patientId}/diseases")
    public ResponseEntity<List<DiseaseResponseDto>> getPatientAllDiseases(@PathVariable UUID patientId) {
        List<DiseaseResponseDto> diseaseResponseDtoList = patientDiseaseService.getPatientAllDiseases(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDtoList);
    }

    @Operation(
            summary = "Assign disease to patient"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Disease assigned to patient successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatientDiseaseResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Disease already assigned to patient",
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
                    description = "Patient / disease not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_DISEASE_ASSIGN)
    @PostMapping("/patients/{patientId}/diseases")
    public ResponseEntity<PatientDiseaseResponseDto> assignDiseaseToPatient(@PathVariable UUID patientId, @Valid @RequestBody AssignDiseaseToPatientRequestDto assignDiseaseToPatientRequestDto) {
        PatientDiseaseResponseDto createdPatientDiseaseResponseDto = patientDiseaseService.assignDiseaseToPatient(patientId, assignDiseaseToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patients/" + createdPatientDiseaseResponseDto.getPatientId() + "/diseases/" + createdPatientDiseaseResponseDto.getDiseaseId()))
                .body(createdPatientDiseaseResponseDto);
    }

    @Operation(
            summary = "Unassign disease from patient"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Disease unassigned from patient successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Disease not assigned to patient",
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
                    description = "Patient / disease not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_DISEASE_UNASSIGN)
    @DeleteMapping("/patients/{patientId}/diseases/{diseaseId}")
    public ResponseEntity<Void> unassignDiseaseFromPatient(@PathVariable UUID patientId, @PathVariable Long diseaseId) {
        patientDiseaseService.unassignDiseaseFromPatient(patientId, diseaseId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
