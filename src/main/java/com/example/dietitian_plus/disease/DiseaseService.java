package com.example.dietitian_plus.disease;

import com.example.dietitian_plus.disease.dto.DiseaseResponseDto;
import com.example.dietitian_plus.disease.dto.DiseaseDtoMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    private final DiseaseDtoMapper diseaseDtoMapper;

    private final String DISEASE_NOT_FOUND_MESSAGE = "Disease not found";

    @Autowired
    public DiseaseService(DiseaseRepository diseaseRepository, DiseaseDtoMapper diseaseDtoMapper) {
        this.diseaseRepository = diseaseRepository;
        this.diseaseDtoMapper = diseaseDtoMapper;
    }

    public List<DiseaseResponseDto> getDiseases() {
        List<Disease> diseases = diseaseRepository.findAll();
        List<DiseaseResponseDto> diseasesDto = new ArrayList<>();

        for (Disease disease : diseases) {
            diseasesDto.add(diseaseDtoMapper.toDto(disease));
        }

        return diseasesDto;
    }

    public DiseaseResponseDto getDiseaseById(Long id) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DISEASE_NOT_FOUND_MESSAGE);
        }

        return diseaseDtoMapper.toDto(diseaseRepository.getReferenceById(id));
    }

    @Transactional
    public DiseaseResponseDto createDisease(DiseaseResponseDto diseaseResponseDto) {
        Disease disease = new Disease();

        disease.setDiseaseName(diseaseResponseDto.getDiseaseName());

        return diseaseDtoMapper.toDto(diseaseRepository.save(disease));
    }

    @Transactional
    public DiseaseResponseDto updateDiseaseById(Long id, DiseaseResponseDto diseaseResponseDto) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DISEASE_NOT_FOUND_MESSAGE);
        }

        Disease disease = diseaseRepository.getReferenceById(id);

        if (diseaseResponseDto.getDiseaseName() != null) {
            disease.setDiseaseName(diseaseResponseDto.getDiseaseName());
        }

        return diseaseDtoMapper.toDto(diseaseRepository.save(disease));
    }

    @Transactional
    public void deleteDiseaseById(Long id) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DISEASE_NOT_FOUND_MESSAGE);
        }

        diseaseRepository.deleteById(id);
    }

}
