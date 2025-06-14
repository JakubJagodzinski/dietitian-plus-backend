package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.auth.access.manager.PatientAccessManager;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final PatientDtoMapper patientDtoMapper;

    private final PatientAccessManager patientAccessManager;

    public List<PatientResponseDto> getAllPatients() {
        return patientDtoMapper.toDtoList(patientRepository.findAll());
    }

    @Transactional
    public PatientResponseDto getPatientById(UUID patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientAccessManager.checkCanViewPatient(patient);

        return patientDtoMapper.toDto(patient);
    }

    @Transactional
    public List<PatientResponseDto> getDietitianAllPatients(UUID dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        patientAccessManager.checkCanViewDietitianPatients(dietitianId);

        return patientDtoMapper.toDtoList(patientRepository.findAllByDietitian_UserId(dietitianId));
    }

    @Transactional
    public PatientResponseDto updatePatientById(UUID patientId, PatientResponseDto patientResponseDto) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientAccessManager.checkCanUpdatePatient(patient);

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
    public void assignDietitianToPatient(UUID patientId, AssignDietitianToPatientRequestDto assignDietitianToPatientRequestDto) throws EntityNotFoundException, IllegalArgumentException {
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

        patientAccessManager.checkCanAssignDietitianToPatient(dietitian);

        patient.setDietitian(dietitian);

        patientRepository.save(patient);
    }

    @Transactional
    public void unassignDietitianFromPatient(UUID patientId) throws EntityNotFoundException, IllegalArgumentException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        if (patient.getDietitian() == null) {
            throw new IllegalArgumentException(PatientMessages.NO_DIETITIAN_ASSIGNED_TO_PATIENT);
        }

        patientAccessManager.checkCanUnassignDietitianFromPatient(patient);

        patient.setDietitian(null);

        patientRepository.save(patient);
    }

    @Transactional
    public void deletePatientById(UUID patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientAccessManager.checkCanDeletePatient(patient);

        patientRepository.deleteById(patientId);
    }

}
