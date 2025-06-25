package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.common.constants.messages.UnitMessages;
import com.example.dietitian_plus.domain.unit.dto.request.CreateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.UnitDtoMapper;
import com.example.dietitian_plus.domain.unit.dto.response.UnitResponseDto;
import com.example.dietitian_plus.domain.unit.dto.request.UpdateUnitRequestDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    private final UnitDtoMapper unitDtoMapper;

    public List<UnitResponseDto> getAllUnits() {
        return unitDtoMapper.toDtoList(unitRepository.findAll());
    }

    @Transactional
    public UnitResponseDto getUnitById(Long unitId) throws EntityNotFoundException {
        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(UnitMessages.UNIT_NOT_FOUND);
        }

        return unitDtoMapper.toDto(unit);
    }

    @Transactional
    public UnitResponseDto createUnit(CreateUnitRequestDto createUnitRequestDto) throws EntityExistsException, IllegalArgumentException {
        if (unitRepository.existsByUnitName(createUnitRequestDto.getUnitName())) {
            throw new EntityExistsException(UnitMessages.UNIT_ALREADY_EXISTS);
        }

        Unit unit = new Unit();

        if (createUnitRequestDto.getUnitName() == null) {
            throw new IllegalArgumentException(UnitMessages.UNIT_NAME_CANNOT_BE_NULL);
        }

        if (createUnitRequestDto.getUnitName().isEmpty()) {
            throw new IllegalArgumentException(UnitMessages.UNIT_NAME_CANNOT_BE_EMPTY);
        }

        unit.setUnitName(createUnitRequestDto.getUnitName());

        if (createUnitRequestDto.getGrams() <= 0) {
            throw new IllegalArgumentException(UnitMessages.UNIT_GRAMS_MUST_BE_POSITIVE);
        }

        unit.setGrams(createUnitRequestDto.getGrams());

        return unitDtoMapper.toDto(unitRepository.save(unit));
    }

    @Transactional
    public UnitResponseDto updateUnitById(Long unitId, UpdateUnitRequestDto updateUnitRequestDto) throws EntityNotFoundException, EntityExistsException {
        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(UnitMessages.UNIT_NOT_FOUND);
        }

        if (updateUnitRequestDto.getUnitName() != null) {
            String unitName = updateUnitRequestDto.getUnitName();

            if (updateUnitRequestDto.getUnitName().isEmpty()) {
                throw new IllegalArgumentException(UnitMessages.UNIT_NAME_CANNOT_BE_EMPTY);
            }

            Unit otherUnit = unitRepository.findByUnitName(unitName).orElse(null);

            if (otherUnit != null && !otherUnit.getUnitId().equals(unit.getUnitId())) {
                throw new EntityExistsException(UnitMessages.UNIT_ALREADY_EXISTS);
            }

            unit.setUnitName(unitName);
        }

        if (updateUnitRequestDto.getGrams() != null) {
            if (updateUnitRequestDto.getGrams() <= 0) {
                throw new IllegalArgumentException(UnitMessages.UNIT_GRAMS_MUST_BE_POSITIVE);
            }

            unit.setGrams(updateUnitRequestDto.getGrams());
        }

        return unitDtoMapper.toDto(unitRepository.save(unit));
    }

    @Transactional
    public void deleteUnitById(Long unitId) throws EntityNotFoundException {
        if (!unitRepository.existsById(unitId)) {
            throw new EntityNotFoundException(UnitMessages.UNIT_NOT_FOUND);
        }

        unitRepository.deleteById(unitId);
    }

}
