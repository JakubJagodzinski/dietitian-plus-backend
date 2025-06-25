package com.example.dietitian_plus.domain.disease;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.disease.dto.request.CreateDiseaseRequestDto;
import com.example.dietitian_plus.domain.disease.dto.response.DiseaseResponseDto;
import com.example.dietitian_plus.domain.disease.dto.request.UpdateDiseaseRequestDto;
import com.example.dietitian_plus.user.Permission;
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

    @CheckPermission(Permission.DISEASE_READ_ALL)
    @GetMapping("/diseases")
    public ResponseEntity<List<DiseaseResponseDto>> getAllDiseases() {
        List<DiseaseResponseDto> diseaseResponseDtoList = diseaseService.getAllDiseases();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDtoList);
    }

    @CheckPermission(Permission.DISEASE_READ)
    @GetMapping("/diseases/{diseaseId}")
    public ResponseEntity<DiseaseResponseDto> getDiseaseById(@PathVariable Long diseaseId) {
        DiseaseResponseDto diseaseResponseDto = diseaseService.getDiseaseById(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diseaseResponseDto);
    }

    @CheckPermission(Permission.DISEASE_CREATE)
    @PostMapping("/diseases")
    public ResponseEntity<DiseaseResponseDto> createDisease(@RequestBody CreateDiseaseRequestDto createDiseaseRequestDto) {
        DiseaseResponseDto createdDiseaseResponseDto = diseaseService.createDisease(createDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/diseases/" + createdDiseaseResponseDto.getDiseaseId()))
                .body(createdDiseaseResponseDto);
    }

    @CheckPermission(Permission.DISEASE_UPDATE)
    @PatchMapping("/diseases/{diseaseId}")
    public ResponseEntity<DiseaseResponseDto> updateDiseaseById(@PathVariable Long diseaseId, @RequestBody UpdateDiseaseRequestDto updateDiseaseRequestDto) {
        DiseaseResponseDto updatedDiseaseResponseDto = diseaseService.updateDiseaseById(diseaseId, updateDiseaseRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDiseaseResponseDto);
    }

    @CheckPermission(Permission.DISEASE_DELETE)
    @DeleteMapping("/diseases/{diseaseId}")
    public ResponseEntity<MessageResponseDto> deleteDiseaseById(@PathVariable Long diseaseId) {
        diseaseService.deleteDiseaseById(diseaseId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Disease with id " + diseaseId + " deleted successfully"));
    }

}
