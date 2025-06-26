package com.example.dietitian_plus.domain.patientdiseases;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.disease.dto.response.DiseaseResponseDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.request.AssignDiseaseToPatientRequestDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.response.PatientDiseaseResponseDto;
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
public class PatientDiseaseController {

    private final PatientDiseaseService patientDiseaseService;

    @CheckPermission(Permission.PATIENT_DISEASE_READ_ALL)
    @GetMapping("/patients/{patientId}/diseases")
    public ResponseEntity<List<DiseaseResponseDto>> getPatientAllDiseases(@PathVariable UUID patientId) {
        List<DiseaseResponseDto> diseaseResponseDtoList = patientDiseaseService.getPatientAllDiseases(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDtoList);
    }

    @CheckPermission(Permission.PATIENT_DISEASE_ASSIGN)
    @PostMapping("/patients/{patientId}/diseases")
    public ResponseEntity<PatientDiseaseResponseDto> assignDiseaseToPatient(@PathVariable UUID patientId, @RequestBody AssignDiseaseToPatientRequestDto assignDiseaseToPatientRequestDto) {
        PatientDiseaseResponseDto createdPatientDiseaseResponseDto = patientDiseaseService.assignDiseaseToPatient(patientId, assignDiseaseToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patients/" + createdPatientDiseaseResponseDto.getPatientId() + "/diseases/" + createdPatientDiseaseResponseDto.getDiseaseId()))
                .body(createdPatientDiseaseResponseDto);
    }

    @CheckPermission(Permission.PATIENT_DISEASE_UNASSIGN)
    @DeleteMapping("/patients/{patientId}/diseases/{diseaseId}")
    public ResponseEntity<Void> unassignDiseaseFromPatient(@PathVariable UUID patientId, @PathVariable Long diseaseId) {
        patientDiseaseService.unassignDiseaseFromPatient(patientId, diseaseId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
