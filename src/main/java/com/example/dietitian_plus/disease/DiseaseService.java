package com.example.dietitian_plus.disease;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    private final DiseaseMapper diseaseMapper;

    private final String DISEASE_NOT_FOUND_MESSAGE = "Disease not found";

    @Autowired
    public DiseaseService(DiseaseRepository diseaseRepository, DiseaseMapper diseaseMapper) {
        this.diseaseRepository = diseaseRepository;
        this.diseaseMapper = diseaseMapper;
    }

    public List<DiseaseDto> getDiseases() {
        List<Disease> diseases = diseaseRepository.findAll();
        List<DiseaseDto> diseasesDto = new ArrayList<>();

        for (Disease disease : diseases) {
            diseasesDto.add(diseaseMapper.toDto(disease));
        }

        return diseasesDto;
    }

    public DiseaseDto getDiseaseById(Long id) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DISEASE_NOT_FOUND_MESSAGE);
        }

        return diseaseMapper.toDto(diseaseRepository.getReferenceById(id));
    }

    @Transactional
    public DiseaseDto createDisease(DiseaseDto diseaseDto) {
        Disease disease = new Disease();

        disease.setDiseaseName(diseaseDto.getDiseaseName());

        return diseaseMapper.toDto(diseaseRepository.save(disease));
    }

    @Transactional
    public DiseaseDto updateDiseaseById(Long id, DiseaseDto diseaseDto) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DISEASE_NOT_FOUND_MESSAGE);
        }

        Disease disease = diseaseRepository.getReferenceById(id);

        if (diseaseDto.getDiseaseName() != null) {
            disease.setDiseaseName(diseaseDto.getDiseaseName());
        }

        return diseaseMapper.toDto(diseaseRepository.save(disease));
    }

    @Transactional
    public void deleteDiseaseById(Long id) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DISEASE_NOT_FOUND_MESSAGE);
        }

        diseaseRepository.deleteById(id);
    }

}
