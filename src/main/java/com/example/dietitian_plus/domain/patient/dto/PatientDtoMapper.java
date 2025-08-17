package com.example.dietitian_plus.domain.patient.dto;

import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.dto.response.PatientResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientDtoMapper {

    public PatientResponseDto toDto(Patient patient) {
        PatientResponseDto patientResponseDto = new PatientResponseDto();

        patientResponseDto.setPatientId(patient.getUserId());
        patientResponseDto.setEmail(patient.getEmail());
        patientResponseDto.setFirstName(patient.getFirstName());
        patientResponseDto.setLastName(patient.getLastName());
        patientResponseDto.setHeight(patient.getHeight());
        patientResponseDto.setStartingWeight(patient.getStartingWeight());
        patientResponseDto.setCurrentWeight(patient.getCurrentWeight());
        patientResponseDto.setPal(patient.getPal());
        patientResponseDto.setBirthdate(patient.getBirthdate());

        if (patient.getDietitian() != null) {
            patientResponseDto.setDietitianId(patient.getDietitian().getUserId());
        }

        patientResponseDto.setVerified(patient.isVerified());

        return patientResponseDto;
    }

    public List<PatientResponseDto> toDtoList(List<Patient> patients) {
        return patients.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
