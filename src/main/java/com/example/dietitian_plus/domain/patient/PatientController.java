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

    @GetMapping("/")
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        List<PatientResponseDto> patientResponseDtoList = patientService.getPatients();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        PatientResponseDto patientResponseDto = patientService.getPatientById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDto);
    }

    @GetMapping("/{id}/meals")
    public ResponseEntity<List<MealResponseDto>> getPatientMeals(@PathVariable Long id) {
        List<MealResponseDto> mealResponseDtoList = patientService.getPatientMeals(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatientById(@PathVariable Long id, @RequestBody PatientResponseDto patientResponseDto) {
        PatientResponseDto updatedPatientResponseDto = patientService.updatePatientById(id, patientResponseDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPatientResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deletePatientById(@PathVariable Long id) {
        patientService.deletePatientById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Patient with id " + id + " deleted successfully"));
    }

}
