package com.example.dietitian_plus.domain.patientdiseases;

import com.example.dietitian_plus.auth.access.manager.PatientDiseaseAccessManager;
import com.example.dietitian_plus.common.constants.messages.DiseaseMessages;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.disease.Disease;
import com.example.dietitian_plus.domain.disease.DiseaseRepository;
import com.example.dietitian_plus.domain.disease.dto.DiseaseDtoMapper;
import com.example.dietitian_plus.domain.disease.dto.DiseaseResponseDto;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import com.example.dietitian_plus.domain.patientdiseases.dto.AssignDiseaseToPatientRequestDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.PatientDiseaseDtoMapper;
import com.example.dietitian_plus.domain.patientdiseases.dto.PatientDiseaseResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientDiseaseService {

    private final PatientDiseaseRepository patientDiseaseRepository;
    private final PatientRepository patientRepository;
    private final DiseaseRepository diseaseRepository;

    private final PatientDiseaseDtoMapper patientDiseaseDtoMapper;
    private final DiseaseDtoMapper diseaseDtoMapper;

    private final PatientDiseaseAccessManager patientDiseaseAccessManager;

    @Transactional
    public List<DiseaseResponseDto> getPatientAllDiseases(UUID patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientDiseaseAccessManager.checkCanAccessPatientDiseases(patient);

        List<PatientDisease> patientDiseaseList = patientDiseaseRepository.findAllByPatient_UserId(patientId);

        return patientDiseaseList.stream()
                .map(PatientDisease::getDisease)
                .map(diseaseDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PatientDiseaseResponseDto assignDiseaseToPatient(UUID patientId, AssignDiseaseToPatientRequestDto assignDiseaseToPatientRequestDto) throws EntityNotFoundException, EntityExistsException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientDiseaseAccessManager.checkCanAssignDiseaseToPatient(patient.getUserId());

        Long diseaseId = assignDiseaseToPatientRequestDto.getDiseaseId();
        Disease disease = diseaseRepository.findById(diseaseId).orElse(null);

        if (disease == null) {
            throw new EntityNotFoundException(DiseaseMessages.DISEASE_NOT_FOUND);
        }

        PatientDiseaseId patientDiseaseId = new PatientDiseaseId(patientId, diseaseId);

        if (patientDiseaseRepository.existsById(patientDiseaseId)) {
            throw new EntityExistsException(DiseaseMessages.DISEASE_ALREADY_ASSIGNED_TO_PATIENT);
        }

        PatientDisease patientDisease = new PatientDisease();

        patientDisease.setId(patientDiseaseId);
        patientDisease.setPatient(patient);
        patientDisease.setDisease(disease);

        return patientDiseaseDtoMapper.toDto(patientDiseaseRepository.save(patientDisease));
    }

    @Transactional
    public void unassignDiseaseFromPatient(UUID patientId, Long diseaseId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        patientDiseaseAccessManager.checkCanUnassignDiseaseFromPatient(patientId);

        if (!diseaseRepository.existsById(diseaseId)) {
            throw new EntityNotFoundException(DiseaseMessages.DISEASE_NOT_FOUND);
        }

        PatientDiseaseId patientDiseaseId = new PatientDiseaseId(patientId, diseaseId);

        if (!patientDiseaseRepository.existsById(patientDiseaseId)) {
            throw new EntityNotFoundException(DiseaseMessages.DISEASE_NOT_ASSIGNED_TO_PATIENT);
        }

        patientDiseaseRepository.deleteById(patientDiseaseId);
    }

}
