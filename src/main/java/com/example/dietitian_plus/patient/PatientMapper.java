package com.example.dietitian_plus.patient;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientMapper {

    public PatientDto toDto(Patient patient) {
        PatientDto patientDto = new PatientDto();

        patientDto.setPatientId(patient.getPatientId());
        patientDto.setEmail(patient.getEmail());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setHeight(patient.getHeight());
        patientDto.setStartingWeight(patient.getStartingWeight());
        patientDto.setCurrentWeight(patient.getCurrentWeight());
        patientDto.setDietitianId(patient.getDietitian().getDietitianId());
        patientDto.setIsActive(patient.getIsActive());

        return patientDto;
    }

    public List<PatientDto> toDtoList(List<Patient> patients) {
        return patients.stream().map(this::toDto).collect(Collectors.toList());
    }

}
