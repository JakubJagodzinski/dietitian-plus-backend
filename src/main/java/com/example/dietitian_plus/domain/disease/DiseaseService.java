package com.example.dietitian_plus.domain.disease;

import com.example.dietitian_plus.domain.disease.dto.DiseaseDtoMapper;
import com.example.dietitian_plus.domain.disease.dto.DiseaseResponseDto;
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

    public List<DiseaseResponseDto> getDiseases() {
        return diseaseDtoMapper.toDtoList(diseaseRepository.findAll());
    }

    @Transactional
    public DiseaseResponseDto getDiseaseById(Long id) throws EntityNotFoundException {
        if (!diseaseRepository.existsById(id)) {
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
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
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
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
            throw new EntityNotFoundException(DISEASE_NOT_FOUND_MESSAGE);
        }

        diseaseRepository.deleteById(id);
    }

}
