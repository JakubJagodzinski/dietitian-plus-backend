package com.example.dietitian_plus.dietitian;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DietitianService {

    private final DietitianRepository dietitianRepository;

    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public DietitianService(DietitianRepository dietitianRepository) {
        this.dietitianRepository = dietitianRepository;
    }

    public List<DietitianDto> getDietitians() {
        List<Dietitian> dietitians = dietitianRepository.findAll();
        List<DietitianDto> dietitiansDto = new ArrayList<>();

        for (var dietitian : dietitians) {
            dietitiansDto.add(mapToDto(dietitian));
        }

        return dietitiansDto;
    }

    public DietitianDto getDietitianById(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        return mapToDto(dietitianRepository.getReferenceById(id));
    }

    @Transactional
    public DietitianDto createDietitian(CreateDietitianDto createDietitianDto) {
        Dietitian dietitian = new Dietitian();

        dietitian.setEmail(createDietitianDto.getEmail());
        dietitian.setTitle(createDietitianDto.getTitle());
        dietitian.setPassword(createDietitianDto.getPassword());
        dietitian.setFirstName(createDietitianDto.getFirstName());
        dietitian.setLastName(createDietitianDto.getLastName());

        return mapToDto(dietitianRepository.save(dietitian));
    }

    @Transactional
    public DietitianDto updateDietitianById(Long id, DietitianDto dietitianDto) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Dietitian dietitian = dietitianRepository.getReferenceById(id);

        if (dietitianDto.getEmail() != null) {
            dietitian.setEmail(dietitianDto.getEmail());
        }

        if (dietitianDto.getTitle() != null) {
            dietitian.setTitle(dietitianDto.getTitle());
        }

        if (dietitianDto.getPassword() != null) {
            dietitian.setPassword(dietitianDto.getPassword());
        }

        if (dietitianDto.getFirstName() != null) {
            dietitian.setFirstName(dietitianDto.getFirstName());
        }

        if (dietitianDto.getLastName() != null) {
            dietitian.setLastName(dietitianDto.getLastName());
        }

        return mapToDto(dietitianRepository.save(dietitian));
    }

    @Transactional
    public void deleteDietitianById(Long id) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(id)) {
            throw new EntityNotFoundException(this.DIETITIAN_NOT_FOUND_MESSAGE);
        }

        dietitianRepository.deleteById(id);
    }

    private DietitianDto mapToDto(Dietitian dietitian) {
        DietitianDto dto = new DietitianDto();

        dto.setDietitianId(dietitian.getDietitianId());
        dto.setEmail(dietitian.getEmail());
        dto.setPassword(dietitian.getPassword());
        dto.setTitle(dietitian.getTitle());
        dto.setFirstName(dietitian.getFirstName());
        dto.setLastName(dietitian.getLastName());

        return dto;
    }

}
