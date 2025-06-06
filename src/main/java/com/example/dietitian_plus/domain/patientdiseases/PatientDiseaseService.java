package com.example.dietitian_plus.domain.patientdiseases;

import com.example.dietitian_plus.domain.disease.Disease;
import com.example.dietitian_plus.domain.disease.DiseaseRepository;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import com.example.dietitian_plus.domain.patientdiseases.dto.CreatePatientDiseaseRequestDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.PatientDiseaseDtoMapper;
import com.example.dietitian_plus.domain.patientdiseases.dto.PatientDiseaseResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientDiseaseService {

    private final PatientDiseaseRepository patientDiseaseRepository;
    private final PatientRepository patientRepository;
    private final DiseaseRepository diseaseRepository;

    private final PatientDiseaseDtoMapper patientDiseaseDtoMapper;

    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String DISEASE_NOT_FOUND_MESSAGE = "Disease not found";
    private static final String DISEASE_ALREADY_ASSIGNED_TO_PATIENT_MESSAGE = "Disease already assigned to patient";
    private static final String PATIENT_DISEASE_ASSOCIATION_NOT_FOUND_MESSAGE = "Patient disease association not found";

    @Transactional
    public List<PatientDiseaseResponseDto> getPatientDiseasesByPatientId(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return patientDiseaseDtoMapper.toDtoList(patientDiseaseRepository.findById_PatientId(patientId));
    }

    @Transactional
    public List<PatientDiseaseResponseDto> getPatientDiseasesByDiseaseId(Long diseaseId) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(diseaseId)) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        return patientDiseaseDtoMapper.toDtoList(patientDiseaseRepository.findById_DiseaseId(diseaseId));
    }

    @Transactional
    public PatientDiseaseResponseDto createPatientDisease(CreatePatientDiseaseRequestDto createPatientDiseaseRequestDto) throws EntityNotFoundException, EntityExistsException {
        Long patientId = createPatientDiseaseRequestDto.getPatientId();
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Long diseaseId = createPatientDiseaseRequestDto.getDiseaseId();
        Disease disease = diseaseRepository.findById(diseaseId).orElse(null);

        if (disease == null) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        PatientDiseaseId patientDiseaseId = new PatientDiseaseId(patientId, diseaseId);

        if (patientDiseaseRepository.existsById(patientDiseaseId)) {
            throw new EntityExistsException(DISEASE_ALREADY_ASSIGNED_TO_PATIENT_MESSAGE);
        }

        PatientDisease patientDisease = new PatientDisease();

        patientDisease.setId(patientDiseaseId);
        patientDisease.setPatient(patient);
        patientDisease.setDisease(disease);

        return patientDiseaseDtoMapper.toDto(patientDiseaseRepository.save(patientDisease));
    }

    @Transactional
    public void deletePatientDisease(Long patientId, Long diseaseId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!diseaseRepository.existsById(diseaseId)) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        PatientDiseaseId patientDiseaseId = new PatientDiseaseId(patientId, diseaseId);

        if (!patientDiseaseRepository.existsById(patientDiseaseId)) {
            throw new EntityNotFoundException(PATIENT_DISEASE_ASSOCIATION_NOT_FOUND_MESSAGE);
        }

        patientDiseaseRepository.deleteById(patientDiseaseId);
    }

}
