package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.patient.dto.AssignDietitianToPatientRequestDto;
import com.example.dietitian_plus.domain.patient.dto.PatientDtoMapper;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final PatientDtoMapper patientDtoMapper;

    public List<PatientResponseDto> getAllPatients() {
        return patientDtoMapper.toDtoList(patientRepository.findAll());
    }

    @Transactional
    public PatientResponseDto getPatientById(Long patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        return patientDtoMapper.toDto(patient);
    }

    @Transactional
    public List<PatientResponseDto> getDietitianAllPatients(Long dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        return patientDtoMapper.toDtoList(patientRepository.findAllByDietitian_UserId(dietitianId));
    }

    @Transactional
    public PatientResponseDto updatePatientById(Long patientId, PatientResponseDto patientResponseDto) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        if (patientResponseDto.getHeight() != null) {
            patient.setHeight(patientResponseDto.getHeight());
        }

        if (patientResponseDto.getStartingWeight() != null) {
            patient.setStartingWeight(patientResponseDto.getStartingWeight());
        }

        if (patientResponseDto.getCurrentWeight() != null) {
            patient.setCurrentWeight(patientResponseDto.getCurrentWeight());
        }

        if (patientResponseDto.getIsActive() != null) {
            patient.setIsActive(patientResponseDto.getIsActive());
        }

        if (patientResponseDto.getDietitianId() != null) {
            Dietitian dietitian = dietitianRepository.findById(patientResponseDto.getDietitianId()).orElse(null);

            if (dietitian == null) {
                throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
            }

            patient.setDietitian(dietitian);
        }

        return patientDtoMapper.toDto(patientRepository.save(patient));
    }

    @Transactional
    public void assignDietitianToPatient(Long patientId, AssignDietitianToPatientRequestDto assignDietitianToPatientRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        if (patient.getDietitian() != null) {
            throw new IllegalArgumentException(PatientMessages.PATIENT_ALREADY_HAS_A_DIETITIAN_ASSIGNED);
        }

        Dietitian dietitian = dietitianRepository.findById(assignDietitianToPatientRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        patient.setDietitian(dietitian);

        patientRepository.save(patient);
    }

    @Transactional
    public void unassignDietitianFromPatient(Long patientId) throws EntityNotFoundException, IllegalArgumentException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        if (patient.getDietitian() == null) {
            throw new IllegalArgumentException(PatientMessages.NO_DIETITIAN_ASSIGNED_TO_PATIENT);
        }

        patient.setDietitian(null);

        patientRepository.save(patient);
    }

    @Transactional
    public void deletePatientById(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientRepository.deleteById(patientId);
    }
}
