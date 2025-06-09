package com.example.dietitian_plus.domain.patientdiseases;

import com.example.dietitian_plus.domain.disease.Disease;
import com.example.dietitian_plus.domain.disease.DiseaseRepository;
import com.example.dietitian_plus.domain.disease.dto.DiseaseDtoMapper;
import com.example.dietitian_plus.domain.disease.dto.DiseaseResponseDto;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import com.example.dietitian_plus.domain.patient.dto.PatientDtoMapper;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import com.example.dietitian_plus.domain.patientdiseases.dto.AssignDiseaseToPatientRequestDto;
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
    private final DiseaseDtoMapper diseaseDtoMapper;
    private final PatientDtoMapper patientDtoMapper;

    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String DISEASE_NOT_FOUND_MESSAGE = "Disease not found";
    private static final String DISEASE_ALREADY_ASSIGNED_TO_PATIENT_MESSAGE = "Disease already assigned to patient";
    private static final String DISEASE_NOT_ASSIGNED_TO_PATIENT_MESSAGE = "Disease is not assigned to patient";

    @Transactional
    public List<DiseaseResponseDto> getPatientAllDiseases(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        List<PatientDisease> patientDiseaseList = patientDiseaseRepository.findAllByPatient_Id(patientId);

        return patientDiseaseList.stream()
                .map(PatientDisease::getDisease)
                .map(diseaseDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public List<PatientResponseDto> getAllPatientsWithGivenDisease(Long diseaseId) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(diseaseId)) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        List<PatientDisease> patientDiseaseList = patientDiseaseRepository.findAllByDisease_DiseaseId(diseaseId);

        return patientDiseaseList.stream()
                .map(PatientDisease::getPatient)
                .map(patientDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PatientDiseaseResponseDto assignDiseaseToPatient(Long patientId, AssignDiseaseToPatientRequestDto assignDiseaseToPatientRequestDto) throws EntityNotFoundException, EntityExistsException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Long diseaseId = assignDiseaseToPatientRequestDto.getDiseaseId();
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
    public void unassignDiseaseFromPatient(Long patientId, Long diseaseId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!diseaseRepository.existsById(diseaseId)) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        PatientDiseaseId patientDiseaseId = new PatientDiseaseId(patientId, diseaseId);

        if (!patientDiseaseRepository.existsById(patientDiseaseId)) {
            throw new EntityNotFoundException(DISEASE_NOT_ASSIGNED_TO_PATIENT_MESSAGE);
        }

        patientDiseaseRepository.deleteById(patientDiseaseId);
    }

}
