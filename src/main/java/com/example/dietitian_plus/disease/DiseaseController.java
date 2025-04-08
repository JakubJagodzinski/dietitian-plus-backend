package com.example.dietitian_plus.disease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    private final DiseaseService diseaseService;

    @Autowired
    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @GetMapping("/")
    public ResponseEntity<List<DiseaseDto>> getDiseases() {
        return ResponseEntity.ok(diseaseService.getDiseases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseDto> getDiseaseById(@PathVariable Long id) {
        return ResponseEntity.ok(diseaseService.getDiseaseById(id));
    }

    @PostMapping("/")
    public ResponseEntity<DiseaseDto> createDisease(@RequestBody DiseaseDto diseaseDto) {
        DiseaseDto createdDiseaseDto = diseaseService.createDisease(diseaseDto);
        return ResponseEntity
                .created(URI.create("/api/diseases/" + createdDiseaseDto.getDiseaseId()))
                .body(createdDiseaseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiseaseDto> updateDiseaseById(@PathVariable Long id, @RequestBody DiseaseDto diseaseDto) {
        return ResponseEntity.ok(diseaseService.updateDiseaseById(id, diseaseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiseaseById(@PathVariable Long id) {
        diseaseService.deleteDiseaseById(id);
        return ResponseEntity.ok("Disease deleted successfully");
    }

}
