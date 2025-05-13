package com.example.dietitian_plus.disease.dto;

import com.example.dietitian_plus.disease.Disease;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiseaseDtoMapper {

    public DiseaseResponseDto toDto(Disease disease) {
        DiseaseResponseDto dto = new DiseaseResponseDto();

        dto.setDiseaseId(disease.getDiseaseId());
        dto.setDiseaseName(disease.getDiseaseName());

        return dto;
    }

    public List<DiseaseResponseDto> toDtoList(List<Disease> diseases) {
        return diseases.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
