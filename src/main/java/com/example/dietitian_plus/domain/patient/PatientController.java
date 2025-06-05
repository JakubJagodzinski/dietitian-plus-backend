package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.meal.dto.MealResponseDto;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        List<PatientResponseDto> patientResponseDtoList = patientService.getPatients();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long patientId) {
        PatientResponseDto patientResponseDto = patientService.getPatientById(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDto);
    }

    @GetMapping("/{patientId}/meals")
    public ResponseEntity<List<MealResponseDto>> getPatientMeals(@PathVariable Long patientId) {
        List<MealResponseDto> mealResponseDtoList = patientService.getPatientMeals(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDtoList);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponseDto> updatePatientById(@PathVariable Long patientId, @RequestBody PatientResponseDto patientResponseDto) {
        PatientResponseDto updatedPatientResponseDto = patientService.updatePatientById(patientId, patientResponseDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPatientResponseDto);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<MessageResponseDto> deletePatientById(@PathVariable Long patientId) {
        patientService.deletePatientById(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Patient with id " + patientId + " deleted successfully"));
    }

}
