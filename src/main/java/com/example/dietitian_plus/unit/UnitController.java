package com.example.dietitian_plus.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UnitDto>> getUnits() {
        return ResponseEntity.ok(unitService.getUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @PostMapping("/")
    public ResponseEntity<UnitDto> createUnit(@RequestBody CreateUnitDto createUnitDto) {
        UnitDto createdUnitDto = unitService.createUnit(createUnitDto);
        return ResponseEntity
                .created(URI.create("/api/uints/" + createdUnitDto.getUnitId()))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitDto> updateUnitById(@PathVariable Long id, @RequestBody UpdateUnitDto updateUnitDto) {
        return ResponseEntity.ok(unitService.updateUnitById(id, updateUnitDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnitById(@PathVariable Long id) {
        unitService.deleteUnitById(id);
        return ResponseEntity.ok("Unit deleted successfully");
    }

}
