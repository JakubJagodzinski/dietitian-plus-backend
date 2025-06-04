package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.auth.dto.RegisterRequestDto;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianDtoMapper;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dish.DishRepository;
import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import com.example.dietitian_plus.domain.patient.dto.PatientDtoMapper;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import com.example.dietitian_plus.user.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DietitianService {

    private final DietitianRepository dietitianRepository;
    private final PatientRepository patientRepository;
    private final DishRepository dishRepository;
    private final PasswordEncoder passwordEncoder;

    private final DietitianDtoMapper dietitianDtoMapper;
    private final PatientDtoMapper patientDtoMapper;
    private final DishDtoMapper dishDtoMapper;

    private static final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

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
    public Dietitian register(RegisterRequestDto registerRequestDto) {
        Dietitian dietitian = new Dietitian();

        dietitian.setFirstName(registerRequestDto.getFirstname());
        dietitian.setLastName(registerRequestDto.getLastname());
        dietitian.setEmail(registerRequestDto.getEmail());
        dietitian.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        dietitian.setRole(Role.valueOf(registerRequestDto.getRole()));

        return dietitianRepository.save(dietitian);
    }

    @Transactional
    public DietitianResponseDto updateDietitianById(Long id, DietitianResponseDto dietitianResponseDto) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
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
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        dietitianRepository.deleteById(id);
    }

}
