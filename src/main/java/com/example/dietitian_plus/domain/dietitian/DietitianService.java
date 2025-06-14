package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianDtoMapper;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.UpdateDietitianRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DietitianService {

    private final DietitianRepository dietitianRepository;

    private final DietitianDtoMapper dietitianDtoMapper;

    public List<DietitianResponseDto> getAllDietitians() {
        return dietitianDtoMapper.toDtoList(dietitianRepository.findAll());
    }

    @Transactional
    public DietitianResponseDto getDietitianById(UUID dietitianId) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        return dietitianDtoMapper.toDto(dietitian);
    }

    @Transactional
    public DietitianResponseDto updateDietitianById(UUID dietitianId, UpdateDietitianRequestDto updateDietitianRequestDto) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        if (updateDietitianRequestDto.getTitle() != null) {
            dietitian.setTitle(updateDietitianRequestDto.getTitle());
        }

        return dietitianDtoMapper.toDto(dietitianRepository.save(dietitian));
    }

    @Transactional
    public void deleteDietitianById(UUID dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        dietitianRepository.deleteById(dietitianId);
    }

}
