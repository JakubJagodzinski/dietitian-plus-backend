package com.example.dietitian_plus.patient.dto;

import com.example.dietitian_plus.patient.Patient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientDtoMapper {

    public PatientResponseDto toDto(Patient patient) {
        PatientResponseDto patientResponseDto = new PatientResponseDto();

        patientResponseDto.setPatientId(patient.getPatientId());
        patientResponseDto.setEmail(patient.getEmail());
        patientResponseDto.setFirstName(patient.getFirstName());
        patientResponseDto.setLastName(patient.getLastName());
        patientResponseDto.setHeight(patient.getHeight());
        patientResponseDto.setStartingWeight(patient.getStartingWeight());
        patientResponseDto.setCurrentWeight(patient.getCurrentWeight());
        patientResponseDto.setDietitianId(patient.getDietitian().getDietitianId());
        patientResponseDto.setIsActive(patient.getIsActive());

        return patientResponseDto;
    }

    public List<PatientResponseDto> toDtoList(List<Patient> patients) {
        return patients.stream().map(this::toDto).collect(Collectors.toList());
    }

}
