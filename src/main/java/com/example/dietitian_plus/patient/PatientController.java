package com.example.dietitian_plus.patient;

import com.example.dietitian_plus.disease.DiseaseDto;
import com.example.dietitian_plus.meal.MealDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PatientDto>> getPatients() {
        return ResponseEntity.ok(patientService.getPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping("/{id}/meals")
    public ResponseEntity<List<MealDto>> getPatientMeals(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientMeals(id));
    }

    @GetMapping("/{id}/diseases")
    public ResponseEntity<List<DiseaseDto>> getPatientDiseases(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientDiseases(id));
    }

    @PostMapping("/")
    public ResponseEntity<PatientDto> createPatient(@RequestBody CreatePatientDto createPatientDto) {
        PatientDto createdPatient = patientService.createPatient(createPatientDto);
        return ResponseEntity.created(URI.create("/api/patients/" + createdPatient.getPatientId())).body(createdPatient);
    }

    @PostMapping("/{patientId}/diseases/{diseaseId}")
    public ResponseEntity<List<DiseaseDto>> assignDiseaseToPatient(@PathVariable Long patientId, @PathVariable Long diseaseId) {
        List<DiseaseDto> patientDiseases = patientService.assignDiseaseToPatient(patientId, diseaseId);
        return ResponseEntity.created(URI.create("/api/patients/" + patientId + "/diseases/" + diseaseId)).body(patientDiseases);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatientById(@PathVariable Long id, @RequestBody PatientDto patientDto) {
        return ResponseEntity.ok(patientService.updatePatientById(id, patientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }

}
