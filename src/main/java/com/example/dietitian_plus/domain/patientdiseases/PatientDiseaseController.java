package com.example.dietitian_plus.domain.patientdiseases;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.disease.dto.DiseaseResponseDto;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.CreatePatientDiseaseRequestDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.PatientDiseaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient-diseases")
@RequiredArgsConstructor
public class PatientDiseaseController {

    private final PatientDiseaseService patientDiseaseService;

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<DiseaseResponseDto>> getDiseasesByPatientId(@PathVariable Long patientId) {
        List<DiseaseResponseDto> diseaseResponseDtoList = patientDiseaseService.getPatientDiseasesByPatientId(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDtoList);
    }

    @GetMapping("/by-disease/{diseaseId}")
    public ResponseEntity<List<PatientResponseDto>> getPatientsByDiseaseId(@PathVariable Long diseaseId) {
        List<PatientResponseDto> patientResponseDtoList = patientDiseaseService.getPatientsByDiseaseId(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<PatientDiseaseResponseDto> createPatientDisease(@RequestBody CreatePatientDiseaseRequestDto createPatientDiseaseRequestDto) {
        PatientDiseaseResponseDto createdPatientDiseaseResponseDto = patientDiseaseService.createPatientDisease(createPatientDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/patient-diseases/" + createdPatientDiseaseResponseDto.getPatientId()))
                .body(createdPatientDiseaseResponseDto);
    }

    @DeleteMapping("/{patientId}/{diseaseId}")
    public ResponseEntity<MessageResponseDto> deletePatientDisease(@PathVariable Long patientId, @PathVariable Long diseaseId) {
        patientDiseaseService.deletePatientDisease(patientId, diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Disease with id " + diseaseId + " is no longer assigned to patient with id " + diseaseId));
    }

}
