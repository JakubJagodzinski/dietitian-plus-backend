package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.auth.dto.RegisterRequestDto;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.meal.MealRepository;
import com.example.dietitian_plus.domain.meal.dto.MealDtoMapper;
import com.example.dietitian_plus.domain.meal.dto.MealResponseDto;
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
    private final MealRepository mealRepository;

    private final PatientDtoMapper patientDtoMapper;
    private final MealDtoMapper mealDtoMapper;

    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";
    private final PasswordEncoder passwordEncoder;

    public List<PatientResponseDto> getPatients() {
        return patientDtoMapper.toDtoList(patientRepository.findAll());
    }

    @Transactional
    public PatientResponseDto getPatientById(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return patientDtoMapper.toDto(patientRepository.getReferenceById(id));
    }

    @Transactional
    public List<MealResponseDto> getPatientMeals(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return mealDtoMapper.toDtoList(mealRepository.findByPatient(patientRepository.getReferenceById(id)));
    }

    @Transactional
    public Patient register(RegisterRequestDto registerRequestDto) throws EntityNotFoundException {
        Patient patient = new Patient();

        patient.setFirstName(registerRequestDto.getFirstname());
        patient.setLastName(registerRequestDto.getLastname());
        patient.setEmail(registerRequestDto.getEmail());
        patient.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        patient.setRole(Role.valueOf(registerRequestDto.getRole()));

        return patientRepository.save(patient);
    }

    @Transactional
    public PatientResponseDto updatePatientById(Long id, PatientResponseDto patientResponseDto) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Patient patient = patientRepository.getReferenceById(id);

        if (patientResponseDto.getEmail() != null) {
            patient.setEmail(patientResponseDto.getEmail());
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
            if (!dietitianRepository.existsById(patientResponseDto.getDietitianId())) {
                throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
            }

            Dietitian dietitian = dietitianRepository.getReferenceById(patientResponseDto.getDietitianId());
            patient.setDietitian(dietitian);
        }

        return patientDtoMapper.toDto(patientRepository.save(patient));
    }

    @Transactional
    public void deletePatientById(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        patientRepository.deleteById(id);
    }

}
