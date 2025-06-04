package com.example.dietitian_plus.domain.patientdiseases.dto;

import com.example.dietitian_plus.domain.patientdiseases.PatientDisease;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientDiseaseDtoMapper {

    public PatientDiseaseResponseDto toDto(PatientDisease patientDisease) {
        PatientDiseaseResponseDto patientDiseaseResponseDto = new PatientDiseaseResponseDto();

        patientDiseaseResponseDto.setPatientId(patientDisease.getPatient().getPatientId());
        patientDiseaseResponseDto.setDiseaseId(patientDisease.getDisease().getDiseaseId());

        return patientDiseaseResponseDto;
    }

    public List<PatientDiseaseResponseDto> toDtoList(List<PatientDisease> patientDiseases) {
        return patientDiseases
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
