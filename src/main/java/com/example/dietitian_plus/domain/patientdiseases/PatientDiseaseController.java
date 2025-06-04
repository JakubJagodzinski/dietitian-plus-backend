package com.example.dietitian_plus.domain.patientdiseases;

import com.example.dietitian_plus.domain.patientdiseases.dto.CreatePatientDiseaseRequestDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.PatientDiseaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient-diseases")
public class PatientDiseaseController {

    private final PatientDiseaseService patientDiseaseService;

    public PatientDiseaseController(PatientDiseaseService patientDiseaseService) {
        this.patientDiseaseService = patientDiseaseService;
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<PatientDiseaseResponseDto>> getDiseasesByPatientId(@PathVariable Long patientId) {
        List<PatientDiseaseResponseDto> patientDiseases = patientDiseaseService.getPatientDiseasesByPatientId(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientDiseases);
    }

    @GetMapping("/by-disease/{diseaseId}")
    public ResponseEntity<List<PatientDiseaseResponseDto>> getPatientsByDiseaseId(@PathVariable Long diseaseId) {
        List<PatientDiseaseResponseDto> patientsWithDisease = patientDiseaseService.getPatientDiseasesByDiseaseId(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientsWithDisease);
    }

    @PostMapping("/")
    public ResponseEntity<PatientDiseaseResponseDto> createPatientDisease(@RequestBody CreatePatientDiseaseRequestDto createPatientDiseaseRequestDto) {
        PatientDiseaseResponseDto createdPatientDisease = patientDiseaseService.createPatientDisease(createPatientDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patient-diseases/" + createdPatientDisease.getPatientId()))
                .body(createdPatientDisease);
    }

    @DeleteMapping("/{patientId}/{diseaseId}")
    public ResponseEntity<String> deletePatientDisease(@PathVariable Long patientId, @PathVariable Long diseaseId) {
        patientDiseaseService.deletePatientDisease(patientId, diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Patient disease association deleted successfully");
    }

}
