package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.auth.dto.RegisterRequestDto;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianDtoMapper;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.UpdateDietitianRequestDto;
import com.example.dietitian_plus.user.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DietitianService {

    private final DietitianRepository dietitianRepository;

    private final DietitianDtoMapper dietitianDtoMapper;

    private final PasswordEncoder passwordEncoder;

    private static final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    public List<DietitianResponseDto> getAllDietitians() {
        return dietitianDtoMapper.toDtoList(dietitianRepository.findAll());
    }

    @Transactional
    public DietitianResponseDto getDietitianById(Long dietitianId) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        return dietitianDtoMapper.toDto(dietitian);
    }

    @Transactional
    public Dietitian register(RegisterRequestDto registerRequestDto) {
        Dietitian dietitian = new Dietitian();

        dietitian.setFirstName(registerRequestDto.getFirstName());
        dietitian.setLastName(registerRequestDto.getLastName());
        dietitian.setEmail(registerRequestDto.getEmail());
        dietitian.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        dietitian.setRole(Role.valueOf(registerRequestDto.getRole()));

        return dietitianRepository.save(dietitian);
    }

    @Transactional
    public DietitianResponseDto updateDietitianById(Long dietitianId, UpdateDietitianRequestDto updateDietitianRequestDto) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        if (updateDietitianRequestDto.getFirstName() != null) {
            dietitian.setFirstName(updateDietitianRequestDto.getFirstName());
        }

        if (updateDietitianRequestDto.getLastName() != null) {
            dietitian.setLastName(updateDietitianRequestDto.getLastName());
        }

        if (updateDietitianRequestDto.getTitle() != null) {
            dietitian.setTitle(updateDietitianRequestDto.getTitle());
        }

        return dietitianDtoMapper.toDto(dietitianRepository.save(dietitian));
    }

    @Transactional
    public void deleteDietitianById(Long dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        dietitianRepository.deleteById(dietitianId);
    }

}
