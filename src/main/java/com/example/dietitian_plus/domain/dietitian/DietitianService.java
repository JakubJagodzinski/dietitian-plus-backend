package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.auth.access.manager.DietitianAccessManager;
import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianDtoMapper;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.UpdateDietitianRequestDto;
import com.example.dietitian_plus.domain.mealsdishes.MealDishRepository;
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
    private final MealDishRepository mealDishRepository;

    private final DietitianDtoMapper dietitianDtoMapper;

    private final DietitianAccessManager dietitianAccessManager;

    public List<DietitianResponseDto> getAllDietitians() {
        return dietitianDtoMapper.toDtoList(dietitianRepository.findAll());
    }

    @Transactional
    public DietitianResponseDto getDietitianById(UUID dietitianId) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        dietitianAccessManager.checkCanReadDietitian(dietitianId);

        return dietitianDtoMapper.toDto(dietitian);
    }

    @Transactional
    public DietitianResponseDto updateDietitianById(UUID dietitianId, UpdateDietitianRequestDto updateDietitianRequestDto) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        dietitianAccessManager.checkCanUpdateDietitian(dietitian.getUserId());

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

        dietitianAccessManager.checkCanDeleteDietitian(dietitianId);

        mealDishRepository.deleteAllByDietitianId(dietitianId);

        dietitianRepository.deleteById(dietitianId);
    }

}
