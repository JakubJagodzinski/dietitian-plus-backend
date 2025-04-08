package com.example.dietitian_plus.disease;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiseaseMapper {

    public DiseaseDto toDto(Disease disease) {
        DiseaseDto dto = new DiseaseDto();

        dto.setDiseaseId(disease.getDiseaseId());
        dto.setDiseaseName(disease.getDiseaseName());

        return dto;
    }

    public List<DiseaseDto> toDtoList(List<Disease> diseases) {
        return diseases.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
