package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.domain.unit.dto.CreateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.UnitDtoMapper;
import com.example.dietitian_plus.domain.unit.dto.UnitResponseDto;
import com.example.dietitian_plus.domain.unit.dto.UpdateUnitResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    private final UnitDtoMapper unitDtoMapper;

    private static final String UNIT_NOT_FOUND_MESSAGE = "Unit not found";

    public List<UnitResponseDto> getUnits() {
        List<Unit> units = unitRepository.findAll();
        List<UnitResponseDto> unitsDto = new ArrayList<>();

        for (Unit unit : units) {
            unitsDto.add(unitDtoMapper.toDto(unit));
        }

        return unitsDto;
    }

    public UnitResponseDto getUnitById(Long id) throws EntityNotFoundException {
        if (!unitRepository.existsById(id)) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        return unitDtoMapper.toDto(unitRepository.getReferenceById(id));
    }

    @Transactional
    public UnitResponseDto createUnit(CreateUnitRequestDto createUnitRequestDto) {
        Unit unit = new Unit();

        unit.setUnitName(createUnitRequestDto.getUnitName());
        unit.setGrams(createUnitRequestDto.getGrams());

        return unitDtoMapper.toDto(unitRepository.save(unit));
    }

    @Transactional
    public UnitResponseDto updateUnitById(Long id, UpdateUnitResponseDto updateUnitResponseDto) throws EntityNotFoundException {
        if (!unitRepository.existsById(id)) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        Unit unit = unitRepository.getReferenceById(id);

        if (updateUnitResponseDto.getUnitName() != null) {
            unit.setUnitName(updateUnitResponseDto.getUnitName());
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
