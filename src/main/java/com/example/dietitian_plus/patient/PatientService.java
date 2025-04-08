package com.example.dietitian_plus.patient;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
import com.example.dietitian_plus.disease.Disease;
import com.example.dietitian_plus.disease.DiseaseDto;
import com.example.dietitian_plus.disease.DiseaseMapper;
import com.example.dietitian_plus.disease.DiseaseRepository;
import com.example.dietitian_plus.meal.MealDto;
import com.example.dietitian_plus.meal.MealMapper;
import com.example.dietitian_plus.meal.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;
    private final MealRepository mealRepository;
    private final DiseaseRepository diseaseRepository;

    private final PatientMapper patientMapper;
    private final MealMapper mealMapper;
    private final DiseaseMapper diseaseMapper;

    private final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";
    private final String DISEASE_NOT_FOUND_MESSAGE = "Disease not found";

    private final String DISEASE_ALREADY_ASSIGNED_TO_THIS_PATIENT_MESSAGE = "Disease already assigned to this patient";

    @Autowired
    public PatientService(PatientRepository patientRepository, DietitianRepository dietitianRepository, MealRepository mealRepository, DiseaseRepository diseaseRepository, PatientMapper patientMapper, MealMapper mealMapper, DiseaseMapper diseaseMapper) {
        this.patientRepository = patientRepository;
        this.dietitianRepository = dietitianRepository;
        this.mealRepository = mealRepository;
        this.diseaseRepository = diseaseRepository;
        this.patientMapper = patientMapper;
        this.mealMapper = mealMapper;
        this.diseaseMapper = diseaseMapper;
    }

    public List<PatientDto> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDto> patientsDto = new ArrayList<>();

        for (Patient patient : patients) {
            patientsDto.add(patientMapper.toDto(patient));
        }

        return patientsDto;
    }

    public PatientDto getPatientById(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return patientMapper.toDto(patientRepository.getReferenceById(id));
    }

    public List<MealDto> getPatientMeals(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return mealMapper.toDtoList(mealRepository.findByPatient(patientRepository.getReferenceById(id)));
    }

    public List<DiseaseDto> getPatientDiseases(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        return diseaseMapper.toDtoList(diseaseRepository.findByPatients_patientId(id));
    }

    @Transactional
    public List<DiseaseDto> assignDiseaseToPatient(Long patientId, Long diseaseId) throws EntityNotFoundException, IllegalStateException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!diseaseRepository.existsById(diseaseId)) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        Patient patient = patientRepository.getReferenceById(patientId);

        Disease disease = diseaseRepository.getReferenceById(diseaseId);

        List<Disease> patientDiseases = patient.getDiseases();

        if (patientDiseases.contains(disease)) {
            throw new IllegalStateException(DISEASE_ALREADY_ASSIGNED_TO_THIS_PATIENT_MESSAGE);
        }

        patientDiseases.add(disease);

        patientRepository.save(patient);

        return diseaseMapper.toDtoList(patientDiseases);
    }

    @Transactional
    public PatientDto createPatient(CreatePatientDto createPatientDto) throws EntityNotFoundException {
        Patient patient = new Patient();

        if (!dietitianRepository.existsById(createPatientDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.getReferenceById(createPatientDto.getDietitianId());

        patient.setEmail(createPatientDto.getEmail());
        patient.setPassword(createPatientDto.getPassword());
        patient.setFirstName(createPatientDto.getFirstName());
        patient.setLastName(createPatientDto.getLastName());
        patient.setHeight(createPatientDto.getHeight());
        patient.setStartingWeight(createPatientDto.getStartingWeight());
        patient.setCurrentWeight(createPatientDto.getStartingWeight());
        patient.setIsActive(Boolean.TRUE);
        patient.setDietitian(dietitian);

        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Transactional
    public PatientDto updatePatientById(Long id, PatientDto patientDto) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Patient patient = patientRepository.getReferenceById(id);

        if (patientDto.getEmail() != null) {
            patient.setEmail(patientDto.getEmail());
        }

        if (patientDto.getHeight() != null) {
            patient.setHeight(patientDto.getHeight());
        }

        if (patientDto.getStartingWeight() != null) {
            patient.setStartingWeight(patientDto.getStartingWeight());
        }

        if (patientDto.getCurrentWeight() != null) {
            patient.setCurrentWeight(patientDto.getCurrentWeight());
        }

        if (patientDto.getIsActive() != null) {
            patient.setIsActive(patientDto.getIsActive());
        }

        if (patientDto.getDietitianId() != null) {
            if (!dietitianRepository.existsById(patientDto.getDietitianId())) {
                throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
            }

            Dietitian dietitian = dietitianRepository.getReferenceById(patientDto.getDietitianId());
            patient.setDietitian(dietitian);
        }

        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Transactional
    public void deletePatientById(Long id) throws EntityNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        patientRepository.deleteById(id);
    }

}
