package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.auth.access.annotation.AdminOnly;
import com.example.dietitian_plus.auth.access.annotation.DietitianAccess;
import com.example.dietitian_plus.auth.access.annotation.OwnerDietitianAccess;
import com.example.dietitian_plus.auth.access.annotation.OwnerPatientAccess;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.patient.dto.AssignDietitianToPatientRequestDto;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @AdminOnly
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> patientResponseDtoList = patientService.getAllPatients();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    // TODO check ownership in access manager
    @GetMapping("/patients/{patientId}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long patientId) {
        PatientResponseDto patientResponseDto = patientService.getPatientById(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDto);
    }

    @OwnerDietitianAccess
    @GetMapping("/dietitians/{dietitianId}/patients")
    public ResponseEntity<List<PatientResponseDto>> getDietitianAllPatients(@PathVariable Long dietitianId) {
        List<PatientResponseDto> patientResponseDtoList = patientService.getDietitianAllPatients(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @OwnerPatientAccess
    @PutMapping("/patients/{patientId}")
    public ResponseEntity<PatientResponseDto> updatePatientById(@PathVariable Long patientId, @RequestBody PatientResponseDto patientResponseDto) {
        PatientResponseDto updatedPatientResponseDto = patientService.updatePatientById(patientId, patientResponseDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPatientResponseDto);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @PostMapping("/patients/{patientId}/dietitians")
    public ResponseEntity<MessageResponseDto> assignDietitianToPatient(@PathVariable Long patientId, @RequestBody AssignDietitianToPatientRequestDto assignDietitianToPatientRequestDto) {
        patientService.assignDietitianToPatient(patientId, assignDietitianToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dietitian with id " + assignDietitianToPatientRequestDto.getDietitianId() + " successfully assigned to patient with id "));
    }

    @OwnerPatientAccess
    @DeleteMapping("/patients/{patientId}")
    public ResponseEntity<MessageResponseDto> deletePatientById(@PathVariable Long patientId) {
        patientService.deletePatientById(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Patient with id " + patientId + " deleted successfully"));
    }

}
