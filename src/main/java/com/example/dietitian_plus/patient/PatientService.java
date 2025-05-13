package com.example.dietitian_plus.patient;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
import com.example.dietitian_plus.disease.Disease;
import com.example.dietitian_plus.disease.dto.DiseaseResponseDto;
import com.example.dietitian_plus.disease.dto.DiseaseDtoMapper;
import com.example.dietitian_plus.disease.DiseaseRepository;
import com.example.dietitian_plus.meal.dto.MealResponseDto;
import com.example.dietitian_plus.meal.dto.MealDtoMapper;
import com.example.dietitian_plus.meal.MealRepository;
import com.example.dietitian_plus.patient.dto.CreatePatientRequestDto;
import com.example.dietitian_plus.patient.dto.PatientResponseDto;
import com.example.dietitian_plus.patient.dto.PatientDtoMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;
    private final MealRepository mealRepository;
    private final DiseaseRepository diseaseRepository;

    private final PatientDtoMapper patientDtoMapper;
    private final MealDtoMapper mealDtoMapper;
    private final DiseaseDtoMapper diseaseDtoMapper;

    private final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";
    private final String DISEASE_NOT_FOUND_MESSAGE = "Disease not found";

    private final String DISEASE_ALREADY_ASSIGNED_TO_THIS_PATIENT_MESSAGE = "Disease already assigned to this patient";

    @Autowired
    public PatientService(PatientRepository patientRepository, DietitianRepository dietitianRepository, MealRepository mealRepository, DiseaseRepository diseaseRepository, PatientDtoMapper patientDtoMapper, MealDtoMapper mealDtoMapper, DiseaseDtoMapper diseaseDtoMapper) {
        this.patientRepository = patientRepository;
        this.dietitianRepository = dietitianRepository;
        this.mealRepository = mealRepository;
        this.diseaseRepository = diseaseRepository;
        this.patientDtoMapper = patientDtoMapper;
        this.mealDtoMapper = mealDtoMapper;
        this.diseaseDtoMapper = diseaseDtoMapper;
    }

    public List<PatientResponseDto> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDto> patientsDto = new ArrayList<>();

        for (Patient patient : patients) {
            patientsDto.add(patientDtoMapper.toDto(patient));
        }

        return patientsDto;
    }

    public PatientResponseDto getPatientById(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return patientDtoMapper.toDto(patientRepository.getReferenceById(id));
    }

    public List<MealResponseDto> getPatientMeals(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return mealDtoMapper.toDtoList(mealRepository.findByPatient(patientRepository.getReferenceById(id)));
    }

    public List<DiseaseResponseDto> getPatientDiseases(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return diseaseDtoMapper.toDtoList(diseaseRepository.findByPatients_patientId(id));
    }

    @Transactional
    public List<DiseaseResponseDto> assignDiseaseToPatient(Long patientId, Long diseaseId) throws EntityNotFoundException, IllegalArgumentException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!diseaseRepository.existsById(diseaseId)) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        Patient patient = patientRepository.getReferenceById(patientId);

        Disease disease = diseaseRepository.getReferenceById(diseaseId);

        Set<Disease> patientDiseases = patient.getDiseases();

        if (patientDiseases.contains(disease)) {
            throw new IllegalArgumentException(DISEASE_ALREADY_ASSIGNED_TO_THIS_PATIENT_MESSAGE);
        }

        patientDiseases.add(disease);

        patientRepository.save(patient);

        return diseaseDtoMapper.toDtoList(patientDiseases.stream().toList());
    }

    @Transactional
    public PatientResponseDto createPatient(CreatePatientRequestDto createPatientRequestDto) throws EntityNotFoundException {
        Patient patient = new Patient();

        if (!dietitianRepository.existsById(createPatientRequestDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.getReferenceById(createPatientRequestDto.getDietitianId());

        patient.setEmail(createPatientRequestDto.getEmail());
        patient.setPassword(createPatientRequestDto.getPassword());
        patient.setFirstName(createPatientRequestDto.getFirstName());
        patient.setLastName(createPatientRequestDto.getLastName());
        patient.setHeight(createPatientRequestDto.getHeight());
        patient.setStartingWeight(createPatientRequestDto.getStartingWeight());
        patient.setCurrentWeight(createPatientRequestDto.getStartingWeight());
        patient.setIsActive(Boolean.TRUE);
        patient.setDietitian(dietitian);

        return patientDtoMapper.toDto(patientRepository.save(patient));
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
