package com.example.dietitian_plus.disease;

import com.example.dietitian_plus.disease.dto.DiseaseResponseDto;
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
    public ResponseEntity<List<DiseaseResponseDto>> getDiseases() {
        return ResponseEntity.ok(diseaseService.getDiseases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseResponseDto> getDiseaseById(@PathVariable Long id) {
        return ResponseEntity.ok(diseaseService.getDiseaseById(id));
    }

    @PostMapping("/")
    public ResponseEntity<DiseaseResponseDto> createDisease(@RequestBody DiseaseResponseDto diseaseResponseDto) {
        DiseaseResponseDto createdDiseaseResponseDto = diseaseService.createDisease(diseaseResponseDto);
        return ResponseEntity.created(URI.create("/api/diseases/" + createdDiseaseResponseDto.getDiseaseId())).body(createdDiseaseResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiseaseResponseDto> updateDiseaseById(@PathVariable Long id, @RequestBody DiseaseResponseDto diseaseResponseDto) {
        return ResponseEntity.ok(diseaseService.updateDiseaseById(id, diseaseResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiseaseById(@PathVariable Long id) {
        diseaseService.deleteDiseaseById(id);
        return ResponseEntity.ok("Disease deleted successfully");
    }

}
