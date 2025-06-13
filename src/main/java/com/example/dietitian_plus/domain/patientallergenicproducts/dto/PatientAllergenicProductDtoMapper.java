package com.example.dietitian_plus.domain.patientallergenicproducts.dto;

import com.example.dietitian_plus.domain.patientallergenicproducts.PatientAllergenicProduct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientAllergenicProductDtoMapper {

    public PatientAllergenicProductResponseDto toDto(PatientAllergenicProduct patientAllergenicProduct) {
        PatientAllergenicProductResponseDto patientAllergenicProductResponseDto = new PatientAllergenicProductResponseDto();

        patientAllergenicProductResponseDto.setPatientId(patientAllergenicProduct.getPatient().getUserId());
        patientAllergenicProductResponseDto.setProductId(patientAllergenicProduct.getProduct().getProductId());

        return patientAllergenicProductResponseDto;
    }

    public List<PatientAllergenicProductResponseDto> toDtoList(List<PatientAllergenicProduct> patientAllergenicProductList) {
        return patientAllergenicProductList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
