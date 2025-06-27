package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.patient.dto.request.AssignDietitianToPatientRequestDto;
import com.example.dietitian_plus.domain.patient.dto.request.PatientQuestionnaireRequestDto;
import com.example.dietitian_plus.domain.patient.dto.request.UpdatePatientRequestDto;
import com.example.dietitian_plus.domain.patient.dto.response.PatientQuestionnaireStatusResponseDto;
import com.example.dietitian_plus.domain.patient.dto.response.PatientResponseDto;
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
public class PatientController {

    private final PatientService patientService;

    @Operation(
            summary = "Get all patients"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all patients",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatientResponseDto.class)
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
    @CheckPermission(Permission.PATIENT_READ_ALL)
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> patientResponseDtoList = patientService.getAllPatients();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @Operation(
            summary = "Get patient by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Patient found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatientResponseDto.class)
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
    @CheckPermission(Permission.PATIENT_READ)
    @GetMapping("/patients/{patientId}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable UUID patientId) {
        PatientResponseDto patientResponseDto = patientService.getPatientById(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDto);
    }

    @Operation(
            summary = "Get patient questionnaire status"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Patient questionnaire status",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatientResponseDto.class)
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
    @CheckPermission(Permission.PATIENT_READ)
    @GetMapping("/patients/{patientId}/questionnaire-status")
    public ResponseEntity<PatientQuestionnaireStatusResponseDto> getPatientQuestionnaireStatus(@PathVariable UUID patientId) {
        PatientQuestionnaireStatusResponseDto patientQuestionnaireStatusResponseDto = patientService.getPatientQuestionnaireStatus(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientQuestionnaireStatusResponseDto);
    }


    @Operation(
            summary = "Get dietitian all patients"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of dietitian all patients",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatientResponseDto.class)
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
                    description = "Dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DIETITIAN_PATIENT_READ_ALL)
    @GetMapping("/dietitians/{dietitianId}/patients")
    public ResponseEntity<List<PatientResponseDto>> getDietitianAllPatients(@PathVariable UUID dietitianId) {
        List<PatientResponseDto> patientResponseDtoList = patientService.getDietitianAllPatients(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @Operation(
            summary = "Fill patient questionnaire"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Questionnaire filled successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Patient questionnaire is already filled",
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
                    description = "Patient not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_UPDATE)
    @PostMapping("/patients/{patientId}/questionnaire")
    public ResponseEntity<MessageResponseDto> fillPatientQuestionnaire(@PathVariable UUID patientId, @Valid @RequestBody PatientQuestionnaireRequestDto patientQuestionnaireRequestDto) {
        patientService.fillPatientQuestionnaire(patientId, patientQuestionnaireRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(PatientMessages.PATIENT_QUESTIONNAIRE_FILLED_SUCCESSFULLY));
    }

    @Operation(
            summary = "Update patient by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Patient updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatientResponseDto.class)
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
    @CheckPermission(Permission.PATIENT_UPDATE)
    @PatchMapping("/patients/{patientId}")
    public ResponseEntity<PatientResponseDto> updatePatientById(@PathVariable UUID patientId, @Valid @RequestBody UpdatePatientRequestDto updatePatientRequestDto) {
        PatientResponseDto updatedPatientResponseDto = patientService.updatePatientById(patientId, updatePatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPatientResponseDto);
    }

    @Operation(
            summary = "Assign dietitian to patient"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dietitian assigned to patient successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Patient already has a dietitian assigned",
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
                    description = "Patient / dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_DIETITIAN_ASSIGN)
    @PostMapping("/patients/dietitians")
    public ResponseEntity<MessageResponseDto> assignDietitianToPatient(@RequestParam(value = "patient_email") String patientEmail, @Valid @RequestBody AssignDietitianToPatientRequestDto assignDietitianToPatientRequestDto) {
        patientService.assignDietitianToPatient(patientEmail, assignDietitianToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dietitian with id " + assignDietitianToPatientRequestDto.getDietitianId() + " successfully assigned to patient with email " + patientEmail));
    }

    @Operation(
            summary = "Unassign dietitian from patient"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dietitian unassigned from patient successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dietitian is not assigned to this patient",
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
                    description = "Patient / dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_DIETITIAN_UNASSIGN)
    @DeleteMapping("/patients/{patientId}/dietitians")
    public ResponseEntity<MessageResponseDto> unassignDietitianFromPatient(@PathVariable UUID patientId) {
        patientService.unassignDietitianFromPatient(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dietitian successfully unassigned from patient with id " + patientId));
    }

    @Operation(
            summary = "Delete patient by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Patient deleted successfully"
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
    @CheckPermission(Permission.PATIENT_DELETE)
    @DeleteMapping("/patients/{patientId}")
    public ResponseEntity<Void> deletePatientById(@PathVariable UUID patientId) {
        patientService.deletePatientById(patientId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
