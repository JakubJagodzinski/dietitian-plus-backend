package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.unit.dto.CreateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.UnitResponseDto;
import com.example.dietitian_plus.domain.unit.dto.UpdateUnitResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping("/")
    public ResponseEntity<List<UnitResponseDto>> getUnits() {
        List<UnitResponseDto> unitResponseDtoList = unitService.getUnits();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(unitResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitResponseDto> getUnitById(@PathVariable Long id) {
        UnitResponseDto unitResponseDto = unitService.getUnitById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(unitResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<UnitResponseDto> createUnit(@RequestBody CreateUnitRequestDto createUnitRequestDto) {
        UnitResponseDto createdUnitResponseDto = unitService.createUnit(createUnitRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/units/" + createdUnitResponseDto.getUnitId()))
                .body(createdUnitResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitResponseDto> updateUnitById(@PathVariable Long id, @RequestBody UpdateUnitResponseDto updateUnitResponseDto) {
        UnitResponseDto updatedUnitResponseDto = unitService.updateUnitById(id, updateUnitResponseDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedUnitResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteUnitById(@PathVariable Long id) {
        unitService.deleteUnitById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Unit with id " + id + " deleted successfully"));
    }

}
