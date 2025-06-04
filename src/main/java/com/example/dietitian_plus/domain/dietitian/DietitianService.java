package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.domain.dietitian.dto.CreateDietitianRequestDto;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.DishRepository;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import com.example.dietitian_plus.domain.patient.dto.PatientDtoMapper;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DietitianService {

    private final DietitianRepository dietitianRepository;
    private final PatientRepository patientRepository;

    private final DietitianDtoMapper dietitianDtoMapper;
    private final PatientDtoMapper patientDtoMapper;
    private final DishDtoMapper dishDtoMapper;

    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";
    private final DishRepository dishRepository;

    @Autowired
    public DietitianService(DietitianRepository dietitianRepository, PatientRepository patientRepository, DietitianDtoMapper dietitianDtoMapper, PatientDtoMapper patientDtoMapper, DishDtoMapper dishDtoMapper, DishRepository dishRepository) {
        this.dietitianRepository = dietitianRepository;
        this.patientRepository = patientRepository;
        this.dietitianDtoMapper = dietitianDtoMapper;
        this.patientDtoMapper = patientDtoMapper;
        this.dishDtoMapper = dishDtoMapper;
        this.dishRepository = dishRepository;
    }

    public List<DietitianResponseDto> getDietitians() {
        List<Dietitian> dietitians = dietitianRepository.findAll();
        List<DietitianResponseDto> dietitiansDto = new ArrayList<>();

        for (Dietitian dietitian : dietitians) {
            dietitiansDto.add(dietitianDtoMapper.toDto(dietitian));
        }

        return dietitiansDto;
    }

    public DietitianResponseDto getDietitianById(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        return dietitianDtoMapper.toDto(dietitianRepository.getReferenceById(id));
    }

    public List<PatientResponseDto> getDietitianPatients(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        return patientDtoMapper.toDtoList(patientRepository.findByDietitian(dietitianRepository.getReferenceById(id)));
    }

    public List<DishResponseDto> getDietitianDishes(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        return dishDtoMapper.toDtoList(dishRepository.findByDietitian(dietitianRepository.getReferenceById(id)));
    }

    @Transactional
    public DietitianResponseDto createDietitian(CreateDietitianRequestDto createDietitianRequestDto) {
        Dietitian dietitian = new Dietitian();

        dietitian.setEmail(createDietitianRequestDto.getEmail());
        dietitian.setTitle(createDietitianRequestDto.getTitle());
        dietitian.setPassword(createDietitianRequestDto.getPassword());
        dietitian.setFirstName(createDietitianRequestDto.getFirstName());
        dietitian.setLastName(createDietitianRequestDto.getLastName());

        return dietitianDtoMapper.toDto(dietitianRepository.save(dietitian));
    }

    @Transactional
    public DietitianResponseDto updateDietitianById(Long id, DietitianResponseDto dietitianResponseDto) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.getReferenceById(id);

        if (dietitianResponseDto.getEmail() != null) {
            dietitian.setEmail(dietitianResponseDto.getEmail());
        }

        if (dietitianResponseDto.getTitle() != null) {
            dietitian.setTitle(dietitianResponseDto.getTitle());
        }

        if (dietitianResponseDto.getPassword() != null) {
            dietitian.setPassword(dietitianResponseDto.getPassword());
        }

        if (dietitianResponseDto.getFirstName() != null) {
            dietitian.setFirstName(dietitianResponseDto.getFirstName());
        }

        if (dietitianResponseDto.getLastName() != null) {
            dietitian.setLastName(dietitianResponseDto.getLastName());
        }

        return dietitianDtoMapper.toDto(dietitianRepository.save(dietitian));
    }

    @Transactional
    public void deleteDietitianById(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DIETITIAN_NOT_FOUND_MESSAGE);
        }

        dietitianRepository.deleteById(id);
    }

}
