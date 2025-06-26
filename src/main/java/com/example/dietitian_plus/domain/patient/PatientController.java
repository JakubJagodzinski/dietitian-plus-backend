package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.patient.dto.request.AssignDietitianToPatientRequestDto;
import com.example.dietitian_plus.domain.patient.dto.request.PatientQuestionnaireRequestDto;
import com.example.dietitian_plus.domain.patient.dto.request.UpdatePatientRequestDto;
import com.example.dietitian_plus.domain.patient.dto.response.PatientQuestionnaireStatusResponseDto;
import com.example.dietitian_plus.domain.patient.dto.response.PatientResponseDto;
import com.example.dietitian_plus.user.Permission;
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

    @CheckPermission(Permission.PATIENT_READ_ALL)
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> patientResponseDtoList = patientService.getAllPatients();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @CheckPermission(Permission.PATIENT_READ)
    @GetMapping("/patients/{patientId}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable UUID patientId) {
        PatientResponseDto patientResponseDto = patientService.getPatientById(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDto);
    }

    @CheckPermission(Permission.PATIENT_READ)
    @GetMapping("/patients/{patientId}/questionnaire-status")
    public ResponseEntity<PatientQuestionnaireStatusResponseDto> getPatientQuestionnaireStatus(@PathVariable UUID patientId) {
        PatientQuestionnaireStatusResponseDto patientQuestionnaireStatusResponseDto = patientService.getPatientQuestionnaireStatus(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientQuestionnaireStatusResponseDto);
    }

    @CheckPermission(Permission.DIETITIAN_PATIENT_READ_ALL)
    @GetMapping("/dietitians/{dietitianId}/patients")
    public ResponseEntity<List<PatientResponseDto>> getDietitianAllPatients(@PathVariable UUID dietitianId) {
        List<PatientResponseDto> patientResponseDtoList = patientService.getDietitianAllPatients(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @CheckPermission(Permission.PATIENT_UPDATE)
    @PostMapping("/patients/{patientId}/questionnaire")
    public ResponseEntity<MessageResponseDto> fillPatientQuestionnaire(@PathVariable UUID patientId, @Valid @RequestBody PatientQuestionnaireRequestDto patientQuestionnaireRequestDto) {
        patientService.fillPatientQuestionnaire(patientId, patientQuestionnaireRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(PatientMessages.PATIENT_QUESTIONNAIRE_FILLED_SUCCESSFULLY));
    }

    @CheckPermission(Permission.PATIENT_UPDATE)
    @PatchMapping("/patients/{patientId}")
    public ResponseEntity<PatientResponseDto> updatePatientById(@PathVariable UUID patientId, @Valid @RequestBody UpdatePatientRequestDto updatePatientRequestDto) {
        PatientResponseDto updatedPatientResponseDto = patientService.updatePatientById(patientId, updatePatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPatientResponseDto);
    }

    @CheckPermission(Permission.PATIENT_DIETITIAN_ASSIGN)
    @PostMapping("/patients/dietitians")
    public ResponseEntity<MessageResponseDto> assignDietitianToPatient(@RequestParam(value = "patient_email") String patientEmail, @Valid @RequestBody AssignDietitianToPatientRequestDto assignDietitianToPatientRequestDto) {
        patientService.assignDietitianToPatient(patientEmail, assignDietitianToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dietitian with id " + assignDietitianToPatientRequestDto.getDietitianId() + " successfully assigned to patient with email " + patientEmail));
    }

    @CheckPermission(Permission.PATIENT_DIETITIAN_UNASSIGN)
    @DeleteMapping("/patients/{patientId}/dietitians")
    public ResponseEntity<MessageResponseDto> unassignDietitianFromPatient(@PathVariable UUID patientId) {
        patientService.unassignDietitianFromPatient(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dietitian successfully unassigned from patient with id " + patientId));
    }

    @CheckPermission(Permission.PATIENT_DELETE)
    @DeleteMapping("/patients/{patientId}")
    public ResponseEntity<Void> deletePatientById(@PathVariable UUID patientId) {
        patientService.deletePatientById(patientId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
