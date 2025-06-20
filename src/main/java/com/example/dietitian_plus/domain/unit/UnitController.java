package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.unit.dto.CreateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.UnitResponseDto;
import com.example.dietitian_plus.domain.unit.dto.UpdateUnitRequestDto;
import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @CheckPermission(Permission.UNIT_READ_ALL)
    @GetMapping("/units")
    public ResponseEntity<List<UnitResponseDto>> getAllUnits() {
        List<UnitResponseDto> unitResponseDtoList = unitService.getAllUnits();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(unitResponseDtoList);
    }

    @CheckPermission(Permission.UNIT_READ)
    @GetMapping("/units/{unitId}")
    public ResponseEntity<UnitResponseDto> getUnitById(@PathVariable Long unitId) {
        UnitResponseDto unitResponseDto = unitService.getUnitById(unitId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(unitResponseDto);
    }

    @CheckPermission(Permission.UNIT_CREATE)
    @PostMapping("/units")
    public ResponseEntity<UnitResponseDto> createUnit(@RequestBody CreateUnitRequestDto createUnitRequestDto) {
        UnitResponseDto createdUnitResponseDto = unitService.createUnit(createUnitRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/units/" + createdUnitResponseDto.getUnitId()))
                .body(createdUnitResponseDto);
    }

    @CheckPermission(Permission.UNIT_UPDATE)
    @PatchMapping("/units/{unitId}")
    public ResponseEntity<UnitResponseDto> updateUnitById(@PathVariable Long unitId, @RequestBody UpdateUnitRequestDto updateUnitRequestDto) {
        UnitResponseDto updatedUnitResponseDto = unitService.updateUnitById(unitId, updateUnitRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedUnitResponseDto);
    }

    @CheckPermission(Permission.UNIT_DELETE)
    @DeleteMapping("/units/{unitId}")
    public ResponseEntity<MessageResponseDto> deleteUnitById(@PathVariable Long unitId) {
        unitService.deleteUnitById(unitId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Unit with id " + unitId + " deleted successfully"));
    }

}
