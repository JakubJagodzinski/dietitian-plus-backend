package com.example.dietitian_plus.disease;

import org.springframework.stereotype.Component;

@Component
public class DiseaseMapper {

    public DiseaseDto toDto(Disease disease) {
        DiseaseDto dto = new DiseaseDto();

        dto.setDiseaseId(disease.getDiseaseId());
        dto.setDiseaseName(disease.getDiseaseName());

        return dto;
    }

}
