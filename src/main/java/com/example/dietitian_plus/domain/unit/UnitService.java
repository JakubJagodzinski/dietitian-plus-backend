package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.domain.unit.dto.CreateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.UnitDtoMapper;
import com.example.dietitian_plus.domain.unit.dto.UnitResponseDto;
import com.example.dietitian_plus.domain.unit.dto.UpdateUnitRequestDto;
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

    private static final String UNIT_NOT_FOUND_MESSAGE = "Unit not found";
    private static final String UNIT_ALREADY_EXISTS_MESSAGE = "Unit already exists";
    private static final String UNIT_NAME_CANNOT_BE_NULL_MESSAGE = "Unit name cannot be null";
    private static final String UNIT_NAME_CANNOT_BE_EMPTY_MESSAGE = "Unit name cannot be empty";
    private static final String UNIT_GRAMS_MUST_BE_POSITIVE_MESSAGE = "Unit grams must be positive";

    public List<UnitResponseDto> getAllUnits() {
        return unitDtoMapper.toDtoList(unitRepository.findAll());
    }

    @Transactional
    public UnitResponseDto getUnitById(Long unitId) throws EntityNotFoundException {
        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        return unitDtoMapper.toDto(unit);
    }

    @Transactional
    public UnitResponseDto createUnit(CreateUnitRequestDto createUnitRequestDto) throws EntityExistsException, IllegalArgumentException {
        if (unitRepository.existsByUnitName(createUnitRequestDto.getUnitName())) {
            throw new EntityExistsException(UNIT_ALREADY_EXISTS_MESSAGE);
        }

        Unit unit = new Unit();

        if (createUnitRequestDto.getUnitName() == null) {
            throw new IllegalArgumentException(UNIT_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createUnitRequestDto.getUnitName().isEmpty()) {
            throw new IllegalArgumentException(UNIT_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        unit.setUnitName(createUnitRequestDto.getUnitName());

        if (createUnitRequestDto.getGrams() <= 0) {
            throw new IllegalArgumentException(UNIT_GRAMS_MUST_BE_POSITIVE_MESSAGE);
        }

        unit.setGrams(createUnitRequestDto.getGrams());

        return unitDtoMapper.toDto(unitRepository.save(unit));
    }

    @Transactional
    public UnitResponseDto updateUnitById(Long unitId, UpdateUnitRequestDto updateUnitRequestDto) throws EntityNotFoundException, EntityExistsException {
        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        if (updateUnitRequestDto.getUnitName() != null) {
            String unitName = updateUnitRequestDto.getUnitName();

            if (updateUnitRequestDto.getUnitName().isEmpty()) {
                throw new IllegalArgumentException(UNIT_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            Unit otherUnit = unitRepository.findByUnitName(unitName).orElse(null);

            if (otherUnit != null && !otherUnit.getUnitId().equals(unit.getUnitId())) {
                throw new EntityExistsException(UNIT_ALREADY_EXISTS_MESSAGE);
            }

            unit.setUnitName(unitName);
        }

        if (updateUnitRequestDto.getGrams() != null) {
            if (updateUnitRequestDto.getGrams() <= 0) {
                throw new IllegalArgumentException(UNIT_GRAMS_MUST_BE_POSITIVE_MESSAGE);
            }

            unit.setGrams(updateUnitRequestDto.getGrams());
        }

        return unitDtoMapper.toDto(unitRepository.save(unit));
    }

    @Transactional
    public void deleteUnitById(Long unitId) throws EntityNotFoundException {
        if (!unitRepository.existsById(unitId)) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        unitRepository.deleteById(unitId);
    }

}
