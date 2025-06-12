package com.example.dietitian_plus.domain.disease;

import com.example.dietitian_plus.common.constants.Messages;
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

    public List<DiseaseResponseDto> getAllDiseases() {
        return diseaseDtoMapper.toDtoList(diseaseRepository.findAll());
    }

    @Transactional
    public DiseaseResponseDto getDiseaseById(Long diseaseId) throws EntityNotFoundException {
        Disease disease = diseaseRepository.findById(diseaseId).orElse(null);

        if (disease == null) {
            throw new EntityNotFoundException(Messages.DISEASE_NOT_FOUND);
        }

        return diseaseDtoMapper.toDto(disease);
    }

    @Transactional
    public DiseaseResponseDto createDisease(CreateDiseaseRequestDto createDiseaseRequestDto) throws EntityExistsException {
        if (diseaseRepository.existsByDiseaseName(createDiseaseRequestDto.getDiseaseName())) {
            throw new EntityExistsException(Messages.DISEASE_ALREADY_EXISTS);
        }

        Disease disease = new Disease();

        disease.setDiseaseName(createDiseaseRequestDto.getDiseaseName());
        disease.setDescription(createDiseaseRequestDto.getDescription());

        return diseaseDtoMapper.toDto(diseaseRepository.save(disease));
    }

    @Transactional
    public DiseaseResponseDto updateDiseaseById(Long diseaseId, UpdateDiseaseRequestDto updateDiseaseRequestDto) throws EntityNotFoundException {
        Disease disease = diseaseRepository.findById(diseaseId).orElse(null);

        if (disease == null) {
            throw new EntityNotFoundException(Messages.DISEASE_NOT_FOUND);
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
    public void deleteDiseaseById(Long diseaseId) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(diseaseId)) {
            throw new EntityNotFoundException(Messages.DISEASE_NOT_FOUND);
        }

        diseaseRepository.deleteById(diseaseId);
    }

}
