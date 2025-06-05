package com.example.dietitian_plus.domain.disease;

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
@RequestMapping("/api/v1/diseases")
@RequiredArgsConstructor
public class DiseaseController {

    private final DiseaseService diseaseService;

    @GetMapping
    public ResponseEntity<List<DiseaseResponseDto>> getDiseases() {
        List<DiseaseResponseDto> diseaseResponseDtoList = diseaseService.getDiseases();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDtoList);
    }

    @GetMapping("/{diseaseId}")
    public ResponseEntity<DiseaseResponseDto> getDiseaseById(@PathVariable Long diseaseId) {
        DiseaseResponseDto diseaseResponseDto = diseaseService.getDiseaseById(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDto);
    }

    @PostMapping
    public ResponseEntity<DiseaseResponseDto> createDisease(@RequestBody CreateDiseaseRequestDto createDiseaseRequestDto) {
        DiseaseResponseDto createdDiseaseResponseDto = diseaseService.createDisease(createDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/diseases/" + createdDiseaseResponseDto.getDiseaseId()))
                .body(createdDiseaseResponseDto);
    }

    @PutMapping("/{diseaseId}")
    public ResponseEntity<DiseaseResponseDto> updateDiseaseById(@PathVariable Long diseaseId, @RequestBody UpdateDiseaseRequestDto updateDiseaseRequestDto) {
        DiseaseResponseDto updatedDiseaseResponseDto = diseaseService.updateDiseaseById(diseaseId, updateDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDiseaseResponseDto);
    }

    @DeleteMapping("/{diseaseId}")
    public ResponseEntity<MessageResponseDto> deleteDiseaseById(@PathVariable Long diseaseId) {
        diseaseService.deleteDiseaseById(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Disease with id " + diseaseId + " deleted successfully"));
    }

}
