package com.example.dietitian_plus.domain.disease;

import com.example.dietitian_plus.domain.disease.dto.CreateDiseaseRequestDto;
import com.example.dietitian_plus.domain.disease.dto.DiseaseDtoMapper;
import com.example.dietitian_plus.domain.disease.dto.DiseaseResponseDto;
import com.example.dietitian_plus.domain.disease.dto.UpdateDiseaseRequestDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    private final DiseaseDtoMapper diseaseDtoMapper;

    private static final String DISEASE_NOT_FOUND_MESSAGE = "Disease not found";
    private static final String DISEASE_ALREADY_EXISTS_MESSAGE = "Disease already exists";

    public List<DiseaseResponseDto> getDiseases() {
        return diseaseDtoMapper.toDtoList(diseaseRepository.findAll());
    }

    @Transactional
    public DiseaseResponseDto getDiseaseById(Long id) throws EntityNotFoundException {
        Disease disease = diseaseRepository.findById(id).orElse(null);

        if (disease == null) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        return diseaseDtoMapper.toDto(disease);
    }

    @Transactional
    public DiseaseResponseDto createDisease(CreateDiseaseRequestDto createDiseaseRequestDto) throws EntityExistsException {
        if (diseaseRepository.existsByDiseaseName(createDiseaseRequestDto.getDiseaseName())) {
            throw new EntityExistsException(DISEASE_ALREADY_EXISTS_MESSAGE);
        }

        Disease disease = new Disease();

        disease.setDiseaseName(createDiseaseRequestDto.getDiseaseName());
        disease.setDescription(createDiseaseRequestDto.getDescription());

        return diseaseDtoMapper.toDto(diseaseRepository.save(disease));
    }

    @Transactional
    public DiseaseResponseDto updateDiseaseById(Long id, UpdateDiseaseRequestDto updateDiseaseRequestDto) throws EntityNotFoundException {
        Disease disease = diseaseRepository.findById(id).orElse(null);

        if (disease == null) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        if (updateDiseaseRequestDto.getDiseaseName() != null) {
            disease.setDiseaseName(updateDiseaseRequestDto.getDiseaseName());
        }

        if (updateDiseaseRequestDto.getDescription() != null) {
            disease.setDescription(updateDiseaseRequestDto.getDescription());
        }

        return diseaseDtoMapper.toDto(diseaseRepository.save(disease));
    }

    @Transactional
    public void deleteDiseaseById(Long id) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(id)) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        diseaseRepository.deleteById(id);
    }

}
