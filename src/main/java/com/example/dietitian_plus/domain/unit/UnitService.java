package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.common.constants.messages.UnitMessages;
import com.example.dietitian_plus.domain.unit.dto.UnitDtoMapper;
import com.example.dietitian_plus.domain.unit.dto.request.CreateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.request.UpdateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.response.UnitResponseDto;
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
    public UnitResponseDto createUnit(CreateUnitRequestDto createUnitRequestDto) throws EntityExistsException {
        String unitName = createUnitRequestDto.getUnitName().trim();

        if (unitRepository.existsByUnitName(unitName)) {
            throw new EntityExistsException(UnitMessages.UNIT_ALREADY_EXISTS);
        }

        Unit unit = new Unit();

        unit.setUnitName(unitName);
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
            String unitName = updateUnitRequestDto.getUnitName().trim();

            Unit otherUnit = unitRepository.findByUnitName(unitName).orElse(null);

            if (otherUnit != null && !otherUnit.getUnitId().equals(unit.getUnitId())) {
                throw new EntityExistsException(UnitMessages.UNIT_ALREADY_EXISTS);
            }

            unit.setUnitName(unitName);
        }

        if (updateUnitRequestDto.getGrams() != null) {
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
