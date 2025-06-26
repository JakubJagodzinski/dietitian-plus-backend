package com.example.dietitian_plus.domain.disease;

import com.example.dietitian_plus.common.constants.messages.DiseaseMessages;
import com.example.dietitian_plus.domain.disease.dto.DiseaseDtoMapper;
import com.example.dietitian_plus.domain.disease.dto.request.CreateDiseaseRequestDto;
import com.example.dietitian_plus.domain.disease.dto.request.UpdateDiseaseRequestDto;
import com.example.dietitian_plus.domain.disease.dto.response.DiseaseResponseDto;
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
            throw new EntityNotFoundException(DiseaseMessages.DISEASE_NOT_FOUND);
        }

        return diseaseDtoMapper.toDto(disease);
    }

    @Transactional
    public DiseaseResponseDto createDisease(CreateDiseaseRequestDto createDiseaseRequestDto) throws EntityExistsException {
        String diseaseName = createDiseaseRequestDto.getDiseaseName().trim();

        if (diseaseRepository.existsByDiseaseName(diseaseName)) {
            throw new EntityExistsException(DiseaseMessages.DISEASE_ALREADY_EXISTS);
        }

        Disease disease = new Disease();

        disease.setDiseaseName(diseaseName);
        disease.setDescription(createDiseaseRequestDto.getDescription().trim());

        return diseaseDtoMapper.toDto(diseaseRepository.save(disease));
    }

    @Transactional
    public DiseaseResponseDto updateDiseaseById(Long diseaseId, UpdateDiseaseRequestDto updateDiseaseRequestDto) throws EntityNotFoundException, EntityExistsException {
        Disease disease = diseaseRepository.findById(diseaseId).orElse(null);

        if (disease == null) {
            throw new EntityNotFoundException(DiseaseMessages.DISEASE_NOT_FOUND);
        }

        if (updateDiseaseRequestDto.getDiseaseName() != null) {
            String diseaseName = updateDiseaseRequestDto.getDiseaseName().trim();

            if (diseaseRepository.existsByDiseaseName(diseaseName)) {
                throw new EntityExistsException(DiseaseMessages.DISEASE_ALREADY_EXISTS);
            }

            disease.setDiseaseName(diseaseName);
        }

        if (updateDiseaseRequestDto.getDescription() != null) {
            disease.setDescription(updateDiseaseRequestDto.getDescription().trim());
        }

        return diseaseDtoMapper.toDto(diseaseRepository.save(disease));
    }

    @Transactional
    public void deleteDiseaseById(Long diseaseId) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(diseaseId)) {
            throw new EntityNotFoundException(DiseaseMessages.DISEASE_NOT_FOUND);
        }

        diseaseRepository.deleteById(diseaseId);
    }

}
