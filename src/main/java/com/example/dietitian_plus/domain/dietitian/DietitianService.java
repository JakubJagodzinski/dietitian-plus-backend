package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.common.constants.Messages;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianDtoMapper;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.UpdateDietitianRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DietitianService {

    private final DietitianRepository dietitianRepository;

    private final DietitianDtoMapper dietitianDtoMapper;

    public List<DietitianResponseDto> getAllDietitians() {
        return dietitianDtoMapper.toDtoList(dietitianRepository.findAll());
    }

    @Transactional
    public DietitianResponseDto getDietitianById(Long dietitianId) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        return dietitianDtoMapper.toDto(dietitian);
    }

    @Transactional
    public DietitianResponseDto updateDietitianById(Long dietitianId, UpdateDietitianRequestDto updateDietitianRequestDto) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        if (updateDietitianRequestDto.getTitle() != null) {
            dietitian.setTitle(updateDietitianRequestDto.getTitle());
        }

        return dietitianDtoMapper.toDto(dietitianRepository.save(dietitian));
    }

    @Transactional
    public void deleteDietitianById(Long dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        dietitianRepository.deleteById(dietitianId);
    }

}
