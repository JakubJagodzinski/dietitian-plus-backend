package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.auth.access.manager.PatientAccessManager;
import com.example.dietitian_plus.common.Gender;
import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.common.constants.messages.UserMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.patient.dto.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        patientAccessManager.checkCanReadPatient(patient);

        return patientDtoMapper.toDto(patient);
    }

    @Transactional
    public List<PatientResponseDto> getDietitianAllPatients(UUID dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        patientAccessManager.checkCanReadDietitianPatients(dietitianId);

        return patientDtoMapper.toDtoList(patientRepository.findAllByDietitian_UserId(dietitianId));
    }

    @Transactional
    public PatientResponseDto updatePatientById(UUID patientId, UpdatePatientRequestDto updatePatientRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientAccessManager.checkCanUpdatePatient(patient);

        if (updatePatientRequestDto.getFirstName() != null) {
            if (updatePatientRequestDto.getFirstName().isBlank()) {
                throw new IllegalArgumentException(UserMessages.FIRST_NAME_CANNOT_BE_EMPTY);
            }
            patient.setFirstName(updatePatientRequestDto.getFirstName());
        }

        if (updatePatientRequestDto.getLastName() != null) {
            if (updatePatientRequestDto.getLastName().isBlank()) {
                throw new IllegalArgumentException(UserMessages.LAST_NAME_CANNOT_BE_EMPTY);
            }

            patient.setLastName(updatePatientRequestDto.getLastName());
        }

        if (updatePatientRequestDto.getHeight() != null) {
            if (updatePatientRequestDto.getHeight() <= 0) {
                throw new IllegalArgumentException(PatientMessages.HEIGHT_MUST_BE_GREATER_THAN_ZERO);
            }

            patient.setHeight(updatePatientRequestDto.getHeight());
        }

        if (updatePatientRequestDto.getStartingWeight() != null) {
            if (updatePatientRequestDto.getStartingWeight() <= 0) {
                throw new IllegalArgumentException(PatientMessages.WEIGHT_MUST_BE_GREATER_THAN_ZERO);
            }

            patient.setStartingWeight(updatePatientRequestDto.getStartingWeight());
        }

        if (updatePatientRequestDto.getCurrentWeight() != null) {
            if (updatePatientRequestDto.getCurrentWeight() <= 0) {
                throw new IllegalArgumentException(PatientMessages.WEIGHT_MUST_BE_GREATER_THAN_ZERO);
            }

            patient.setCurrentWeight(updatePatientRequestDto.getCurrentWeight());
        }

        if (updatePatientRequestDto.getPal() != null) {
            patient.setPal(updatePatientRequestDto.getPal());
        }

        if (updatePatientRequestDto.getBirthdate() != null) {
            if (updatePatientRequestDto.getBirthdate().isAfter(java.time.LocalDate.now())) {
                throw new IllegalArgumentException(PatientMessages.BIRTHDATE_CANNOT_BE_FUTURE_DATE);
            }
            patient.setBirthdate(updatePatientRequestDto.getBirthdate());
        }

        return patientDtoMapper.toDto(patientRepository.save(patient));
    }

    @Transactional
    public void assignDietitianToPatient(String patientEmail, AssignDietitianToPatientRequestDto assignDietitianToPatientRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Patient patient = patientRepository.findByEmail(patientEmail).orElse(null);

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

    @Transactional
    public void fillPatientQuestionnaire(UUID patientId, PatientQuestionnaireRequestDto patientQuestionnaireRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientAccessManager.checkCanUpdatePatient(patient);

        if (patient.getIsQuestionnaireCompleted()) {
            throw new IllegalArgumentException(PatientMessages.PATIENT_QUESTIONNAIRE_ALREADY_FILLED);
        }

        if (patientQuestionnaireRequestDto.getHeight() == null) {
            throw new IllegalArgumentException(PatientMessages.HEIGHT_CANNOT_BE_NULL);
        }

        if (patientQuestionnaireRequestDto.getHeight() <= 0) {
            throw new IllegalArgumentException(PatientMessages.HEIGHT_MUST_BE_GREATER_THAN_ZERO);
        }

        patient.setHeight(patientQuestionnaireRequestDto.getHeight());

        if (patientQuestionnaireRequestDto.getStartingWeight() == null) {
            throw new IllegalArgumentException(PatientMessages.WEIGHT_CANNOT_BE_NULL);
        }

        if (patientQuestionnaireRequestDto.getStartingWeight() <= 0) {
            throw new IllegalArgumentException(PatientMessages.WEIGHT_CANNOT_BE_NULL);
        }

        patient.setStartingWeight(patientQuestionnaireRequestDto.getStartingWeight());
        patient.setCurrentWeight(patientQuestionnaireRequestDto.getStartingWeight());

        if (patientQuestionnaireRequestDto.getPal() == null) {
            throw new IllegalArgumentException(PatientMessages.PAL_CANNOT_BE_NULL);
        }

        if (patientQuestionnaireRequestDto.getPal() <= 0) {
            throw new IllegalArgumentException(PatientMessages.PAL_MUST_BE_GREATER_THAN_ZERO);
        }

        patient.setPal(patientQuestionnaireRequestDto.getPal());

        if (patientQuestionnaireRequestDto.getBirthdate() == null) {
            throw new IllegalArgumentException(PatientMessages.BIRTHDATE_CANNOT_BE_NULL);
        }

        if (patientQuestionnaireRequestDto.getBirthdate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(PatientMessages.BIRTHDATE_CANNOT_BE_FUTURE_DATE);
        }

        patient.setBirthdate(patientQuestionnaireRequestDto.getBirthdate());

        if (patientQuestionnaireRequestDto.getGender() == null) {
            throw new IllegalArgumentException(PatientMessages.GENDER_CANNOT_BE_NULL);
        }

        if (!Gender.isValidGender(patientQuestionnaireRequestDto.getGender().toUpperCase())) {
            throw new IllegalArgumentException(PatientMessages.INVALID_GENDER_SPECIFIED);
        }

        patient.setGender(Gender.valueOf(patientQuestionnaireRequestDto.getGender().toUpperCase()));

        patient.setIsQuestionnaireCompleted(true);
    }

    public PatientQuestionnaireStatusResponseDto getPatientQuestionnaireStatus(UUID patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientAccessManager.checkCanReadPatient(patient);

        PatientQuestionnaireStatusResponseDto patientQuestionnaireStatusResponseDto = new PatientQuestionnaireStatusResponseDto();

        patientQuestionnaireStatusResponseDto.setIsQuestionnaireCompleted(patientRepository.existsByUserIdAndIsQuestionnaireCompletedIsTrue(patientId));

        return patientQuestionnaireStatusResponseDto;
    }

}
