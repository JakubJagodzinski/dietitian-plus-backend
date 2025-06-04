package com.example.dietitian_plus.patient;

import com.example.dietitian_plus.meal.dto.MealResponseDto;
import com.example.dietitian_plus.patient.dto.CreatePatientRequestDto;
import com.example.dietitian_plus.patient.dto.PatientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/")
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        return ResponseEntity.ok(patientService.getPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping("/{id}/meals")
    public ResponseEntity<List<MealResponseDto>> getPatientMeals(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientMeals(id));
    }

    @PostMapping("/")
    public ResponseEntity<PatientResponseDto> createPatient(@RequestBody CreatePatientRequestDto createPatientRequestDto) {
        PatientResponseDto createdPatient = patientService.createPatient(createPatientRequestDto);
        return ResponseEntity.created(URI.create("/api/patients/" + createdPatient.getPatientId())).body(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatientById(@PathVariable Long id, @RequestBody PatientResponseDto patientResponseDto) {
        return ResponseEntity.ok(patientService.updatePatientById(id, patientResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }

}
