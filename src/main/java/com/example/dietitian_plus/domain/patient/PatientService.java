package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.auth.access.manager.PatientAccessManager;
import com.example.dietitian_plus.common.Gender;
import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.patient.dto.PatientDtoMapper;
import com.example.dietitian_plus.domain.patient.dto.request.AssignDietitianToPatientRequestDto;
import com.example.dietitian_plus.domain.patient.dto.request.PatientQuestionnaireRequestDto;
import com.example.dietitian_plus.domain.patient.dto.request.UpdatePatientRequestDto;
import com.example.dietitian_plus.domain.patient.dto.response.PatientQuestionnaireStatusResponseDto;
import com.example.dietitian_plus.domain.patient.dto.response.PatientResponseDto;
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
    public PatientResponseDto updatePatientById(UUID patientId, UpdatePatientRequestDto updatePatientRequestDto) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientAccessManager.checkCanUpdatePatient(patient);

        if (updatePatientRequestDto.getFirstName() != null) {
            patient.setFirstName(updatePatientRequestDto.getFirstName());
        }

        if (updatePatientRequestDto.getLastName() != null) {
            patient.setLastName(updatePatientRequestDto.getLastName());
        }

        if (updatePatientRequestDto.getHeight() != null) {
            patient.setHeight(updatePatientRequestDto.getHeight());
        }

        if (updatePatientRequestDto.getCurrentWeight() != null) {
            patient.setCurrentWeight(updatePatientRequestDto.getCurrentWeight());
        }

        if (updatePatientRequestDto.getPal() != null) {
            patient.setPal(updatePatientRequestDto.getPal());
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

        if (patient.isQuestionnaireCompleted()) {
            throw new IllegalArgumentException(PatientMessages.PATIENT_QUESTIONNAIRE_ALREADY_FILLED);
        }

        patient.setHeight(patientQuestionnaireRequestDto.getHeight());
        patient.setStartingWeight(patientQuestionnaireRequestDto.getStartingWeight());
        patient.setCurrentWeight(patientQuestionnaireRequestDto.getStartingWeight());
        patient.setPal(patientQuestionnaireRequestDto.getPal());
        patient.setBirthdate(patientQuestionnaireRequestDto.getBirthdate());

        if (!Gender.isValidGender(patientQuestionnaireRequestDto.getGender().toUpperCase())) {
            throw new IllegalArgumentException(PatientMessages.INVALID_GENDER_SPECIFIED);
        }

        patient.setGender(Gender.valueOf(patientQuestionnaireRequestDto.getGender().toUpperCase()));

        patient.setQuestionnaireCompleted(true);
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
