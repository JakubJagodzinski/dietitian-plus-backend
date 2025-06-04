package com.example.dietitian_plus.patientdiseases;

import com.example.dietitian_plus.disease.Disease;
import com.example.dietitian_plus.disease.DiseaseRepository;
import com.example.dietitian_plus.patient.Patient;
import com.example.dietitian_plus.patient.PatientRepository;
import com.example.dietitian_plus.patientdiseases.dto.CreatePatientDiseaseRequestDto;
import com.example.dietitian_plus.patientdiseases.dto.PatientDiseaseDtoMapper;
import com.example.dietitian_plus.patientdiseases.dto.PatientDiseaseResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientDiseaseService {

    private final PatientDiseaseRepository patientDiseaseRepository;
    private final PatientRepository patientRepository;
    private final DiseaseRepository diseaseRepository;

    private final PatientDiseaseDtoMapper patientDiseaseDtoMapper;

    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String DISEASE_NOT_FOUND_MESSAGE = "Disease not found";
    private static final String DISEASE_ALREADY_ASSIGNED_TO_PATIENT_MESSAGE = "Disease already assigned to patient";
    private static final String PATIENT_DISEASE_ASSOCIATION_NOT_FOUND_MESSAGE = "Patient disease association not found";

    public PatientDiseaseService(PatientDiseaseRepository patientDiseaseRepository, PatientRepository patientRepository, DiseaseRepository diseaseRepository, PatientDiseaseDtoMapper patientDiseaseDtoMapper) {
        this.patientDiseaseRepository = patientDiseaseRepository;
        this.patientRepository = patientRepository;
        this.diseaseRepository = diseaseRepository;
        this.patientDiseaseDtoMapper = patientDiseaseDtoMapper;
    }

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
    public PatientDiseaseResponseDto createPatientDisease(CreatePatientDiseaseRequestDto createPatientDiseaseRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Patient patient = patientRepository.findById(createPatientDiseaseRequestDto.getPatientId()).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Disease disease = diseaseRepository.findById(createPatientDiseaseRequestDto.getDiseaseId()).orElse(null);

        if (disease == null) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        PatientDiseaseId patientDiseaseId = new PatientDiseaseId();

        patientDiseaseId.setPatientId(createPatientDiseaseRequestDto.getPatientId());
        patientDiseaseId.setDiseaseId(createPatientDiseaseRequestDto.getDiseaseId());

        if (patientDiseaseRepository.existsById(patientDiseaseId)) {
            throw new IllegalArgumentException(DISEASE_ALREADY_ASSIGNED_TO_PATIENT_MESSAGE);
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

        PatientDiseaseId patientDiseaseId = new PatientDiseaseId();
        patientDiseaseId.setPatientId(patientId);
        patientDiseaseId.setDiseaseId(diseaseId);

        if (!patientDiseaseRepository.existsById(patientDiseaseId)) {
            throw new EntityNotFoundException(PATIENT_DISEASE_ASSOCIATION_NOT_FOUND_MESSAGE);
        }

        patientDiseaseRepository.deleteById(patientDiseaseId);
    }

}
