package com.example.dietitian_plus.domain.patientdislikedproducts.dto;

import com.example.dietitian_plus.domain.patientdislikedproducts.PatientDislikedProduct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientDislikedProductDtoMapper {

    public PatientDislikedProductResponseDto toDto(PatientDislikedProduct patientDislikedProduct) {
        PatientDislikedProductResponseDto patientDislikedProductResponseDto = new PatientDislikedProductResponseDto();

        patientDislikedProductResponseDto.setPatientId(patientDislikedProduct.getPatient().getUserId());
        patientDislikedProductResponseDto.setProductId(patientDislikedProduct.getProduct().getProductId());

        return patientDislikedProductResponseDto;
    }

    public List<PatientDislikedProductResponseDto> toDtoList(List<PatientDislikedProduct> patientDislikedProductList) {
        return patientDislikedProductList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
