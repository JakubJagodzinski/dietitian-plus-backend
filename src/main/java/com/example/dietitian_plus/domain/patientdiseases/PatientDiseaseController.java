package com.example.dietitian_plus.domain.patientdiseases;

import com.example.dietitian_plus.auth.access.annotation.AdminOnly;
import com.example.dietitian_plus.auth.access.annotation.OwnerPatientAccess;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.disease.dto.DiseaseResponseDto;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.AssignDiseaseToPatientRequestDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.PatientDiseaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PatientDiseaseController {

    private final PatientDiseaseService patientDiseaseService;

    // TODO check ownership in access manager
    @GetMapping("/patients/{patientId}/diseases")
    public ResponseEntity<List<DiseaseResponseDto>> getPatientAllDiseases(@PathVariable Long patientId) {
        List<DiseaseResponseDto> diseaseResponseDtoList = patientDiseaseService.getPatientAllDiseases(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDtoList);
    }

    @AdminOnly
    @GetMapping("/diseases/{diseaseId}/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatientsWithGivenDisease(@PathVariable Long diseaseId) {
        List<PatientResponseDto> patientResponseDtoList = patientDiseaseService.getAllPatientsWithGivenDisease(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @OwnerPatientAccess
    @PostMapping("/patients/{patientId}/diseases")
    public ResponseEntity<PatientDiseaseResponseDto> assignDiseaseToPatient(@PathVariable Long patientId, @RequestBody AssignDiseaseToPatientRequestDto assignDiseaseToPatientRequestDto) {
        PatientDiseaseResponseDto createdPatientDiseaseResponseDto = patientDiseaseService.assignDiseaseToPatient(patientId, assignDiseaseToPatientRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patients/" + createdPatientDiseaseResponseDto.getPatientId() + "/diseases/" + createdPatientDiseaseResponseDto.getDiseaseId()))
                .body(createdPatientDiseaseResponseDto);
    }

    @OwnerPatientAccess
    @DeleteMapping("/patients/{patientId}/diseases/{diseaseId}")
    public ResponseEntity<MessageResponseDto> unassignDiseaseFromPatient(@PathVariable Long patientId, @PathVariable Long diseaseId) {
        patientDiseaseService.unassignDiseaseFromPatient(patientId, diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Disease with id " + diseaseId + " successfully unassigned from patient with id " + patientId));
    }

}
