package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.auth.dto.RegisterRequestDto;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.patient.dto.AssignDietitianToPatientRequestDto;
import com.example.dietitian_plus.domain.patient.dto.PatientDtoMapper;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import com.example.dietitian_plus.user.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final PatientDtoMapper patientDtoMapper;

    private final PasswordEncoder passwordEncoder;

    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    public List<PatientResponseDto> getAllPatients() {
        return patientDtoMapper.toDtoList(patientRepository.findAll());
    }

    @Transactional
    public PatientResponseDto getPatientById(Long patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return patientDtoMapper.toDto(patient);
    }

    @Transactional
    public List<PatientResponseDto> getDietitianAllPatients(Long dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        return patientDtoMapper.toDtoList(patientRepository.findByDietitian_Id(dietitianId));
    }

    @Transactional
    public Patient register(RegisterRequestDto registerRequestDto) {
        Patient patient = new Patient();

        patient.setFirstName(registerRequestDto.getFirstName());
        patient.setLastName(registerRequestDto.getLastName());
        patient.setEmail(registerRequestDto.getEmail());
        patient.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        patient.setRole(Role.valueOf(registerRequestDto.getRole()));

        return patientRepository.save(patient);
    }

    @Transactional
    public PatientResponseDto updatePatientById(Long patientId, PatientResponseDto patientResponseDto) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
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
                throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
            }

            patient.setDietitian(dietitian);
        }

        return patientDtoMapper.toDto(patientRepository.save(patient));
    }

    @Transactional
    public void assignDietitianToPatient(Long patientId, AssignDietitianToPatientRequestDto assignDietitianToPatientRequestDto) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.findById(assignDietitianToPatientRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        patient.setDietitian(dietitian);

        patientRepository.save(patient);
    }

    @Transactional
    public void deletePatientById(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        patientRepository.deleteById(patientId);
    }

}
