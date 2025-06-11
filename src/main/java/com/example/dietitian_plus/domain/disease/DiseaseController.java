package com.example.dietitian_plus.domain.disease;

import com.example.dietitian_plus.auth.access.annotation.AdminOnly;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.disease.dto.CreateDiseaseRequestDto;
import com.example.dietitian_plus.domain.disease.dto.DiseaseResponseDto;
import com.example.dietitian_plus.domain.disease.dto.UpdateDiseaseRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DiseaseController {

    private final DiseaseService diseaseService;

    @GetMapping("/diseases")
    public ResponseEntity<List<DiseaseResponseDto>> getAllDiseases() {
        List<DiseaseResponseDto> diseaseResponseDtoList = diseaseService.getAllDiseases();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDtoList);
    }

    @GetMapping("/diseases/{diseaseId}")
    public ResponseEntity<DiseaseResponseDto> getDiseaseById(@PathVariable Long diseaseId) {
        DiseaseResponseDto diseaseResponseDto = diseaseService.getDiseaseById(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDto);
    }

    @AdminOnly
    @PostMapping("/diseases")
    public ResponseEntity<DiseaseResponseDto> createDisease(@RequestBody CreateDiseaseRequestDto createDiseaseRequestDto) {
        DiseaseResponseDto createdDiseaseResponseDto = diseaseService.createDisease(createDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/diseases/" + createdDiseaseResponseDto.getDiseaseId()))
                .body(createdDiseaseResponseDto);
    }

    @AdminOnly
    @PutMapping("/diseases/{diseaseId}")
    public ResponseEntity<DiseaseResponseDto> updateDiseaseById(@PathVariable Long diseaseId, @RequestBody UpdateDiseaseRequestDto updateDiseaseRequestDto) {
        DiseaseResponseDto updatedDiseaseResponseDto = diseaseService.updateDiseaseById(diseaseId, updateDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDiseaseResponseDto);
    }

    @AdminOnly
    @DeleteMapping("/diseases/{diseaseId}")
    public ResponseEntity<MessageResponseDto> deleteDiseaseById(@PathVariable Long diseaseId) {
        diseaseService.deleteDiseaseById(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Disease with id " + diseaseId + " deleted successfully"));
    }

}
