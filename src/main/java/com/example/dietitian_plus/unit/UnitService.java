package com.example.dietitian_plus.unit;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;
    
    private final UnitMapper unitMapper;

    private final String UNIT_NOT_FOUND_MESSAGE = "Unit not found";

    @Autowired
    public UnitService(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    public List<UnitDto> getUnits() {
        List<Unit> units = unitRepository.findAll();
        List<UnitDto> unitsDto = new ArrayList<>();

        for (Unit unit : units) {
            unitsDto.add(unitMapper.toDto(unit));
        }

        return unitsDto;
    }

    public UnitDto getUnitById(Long id) throws EntityNotFoundException {
        if (!unitRepository.existsById(id)) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        return unitMapper.toDto(unitRepository.getReferenceById(id));
    }

    @Transactional
    public UnitDto createUnit(CreateUnitDto createUnitDto) {
        Unit unit = new Unit();

        unit.setUnitName(createUnitDto.getUnitName());
        unit.setGrams(createUnitDto.getGrams());

        return unitMapper.toDto(unitRepository.save(unit));
    }

    @Transactional
    public UnitDto updateUnitById(Long id, UpdateUnitDto updateUnitDto) throws EntityNotFoundException {
        if (!unitRepository.existsById(id)) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        Unit unit = unitRepository.getReferenceById(id);

        if (updateUnitDto.getUnitName() != null) {
            unit.setUnitName(updateUnitDto.getUnitName());
        }

        if (updateUnitDto.getGrams() != null) {
            unit.setGrams(updateUnitDto.getGrams());
        }

        return unitMapper.toDto(unitRepository.save(unit));
    }

    @Transactional
    public void deleteUnitById(Long id) throws EntityNotFoundException {
        if (!unitRepository.existsById(id)) {
            throw new EntityNotFoundException(UNIT_NOT_FOUND_MESSAGE);
        }

        unitRepository.deleteById(id);
    }

}
