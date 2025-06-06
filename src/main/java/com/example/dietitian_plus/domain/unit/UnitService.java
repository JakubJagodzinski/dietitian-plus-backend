package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.domain.unit.dto.CreateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.UnitDtoMapper;
import com.example.dietitian_plus.domain.unit.dto.UnitResponseDto;
import com.example.dietitian_plus.domain.unit.dto.UpdateUnitResponseDto;
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

    public List<UnitResponseDto> getUnits() {
        return unitDtoMapper.toDtoList(unitRepository.findAll());
    }

    @Transactional
    public UnitResponseDto getUnitById(Long id) throws EntityNotFoundException {
        Unit unit = unitRepository.findById(id).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        return unitDtoMapper.toDto(unit);
    }

    @Transactional
    public UnitResponseDto createUnit(CreateUnitRequestDto createUnitRequestDto) throws EntityExistsException {
        if (unitRepository.existsByUnitName(createUnitRequestDto.getUnitName())) {
            throw new EntityExistsException(UNIT_ALREADY_EXISTS_MESSAGE);
        }

        Unit unit = new Unit();

        unit.setUnitName(createUnitRequestDto.getUnitName());
        unit.setGrams(createUnitRequestDto.getGrams());

        return unitDtoMapper.toDto(unitRepository.save(unit));
    }

    @Transactional
    public UnitResponseDto updateUnitById(Long id, UpdateUnitResponseDto updateUnitResponseDto) throws EntityNotFoundException, EntityExistsException {
        Unit unit = unitRepository.findById(id).orElse(null);

        if (unit == null) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        if (updateUnitResponseDto.getUnitName() != null) {
            String unitName = updateUnitResponseDto.getUnitName();

            Unit otherUnit = unitRepository.findByUnitName(unitName).orElse(null);

            if (otherUnit != null && !otherUnit.getUnitId().equals(unit.getUnitId())) {
                throw new EntityExistsException(UNIT_ALREADY_EXISTS_MESSAGE);
            }

            unit.setUnitName(unitName);
        }

        if (updateUnitResponseDto.getGrams() != null) {
            unit.setGrams(updateUnitResponseDto.getGrams());
        }

        return unitDtoMapper.toDto(unitRepository.save(unit));
    }

    @Transactional
    public void deleteUnitById(Long id) throws EntityNotFoundException {
        if (!unitRepository.existsById(id)) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        unitRepository.deleteById(id);
    }

}
