package dev.orders.controller;

import dev.orders.dto.UnitDTO;
import dev.orders.entity.Unit;
import dev.orders.exception.EntityValidationException;
import dev.orders.service.UnitService;
import dev.orders.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {
    @Autowired
    private UnitService unitService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UnitDTO>> getAllUnits() {
        List<UnitDTO> unitsDTO = unitService.findAllUnits().stream()
                .map(unit -> modelMapper.map(unit, UnitDTO.class))
                .toList();

        return ResponseEntity.ok(unitsDTO);
    }

    @GetMapping("/{shortName}")
    public ResponseEntity<UnitDTO> getUnitByShortName(@PathVariable String shortName) {
        Unit unit = unitService.findByShortName(shortName);
        UnitDTO unitDTO = modelMapper.map(unit, UnitDTO.class);

        return ResponseEntity.ok(unitDTO);
    }

    @PostMapping
    public ResponseEntity<UnitDTO> addUnit(@RequestBody @Valid UnitDTO unitDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = StringUtils.getErrorsFromValidation(bindingResult);

            throw new EntityValidationException(errors);
        }

        String shortName = unitDTO.getShortName();
        String fullName = unitDTO.getFullName();
        unitDTO.setShortName(shortName);
        unitDTO.setFullName(fullName);

        Unit unitFromDTO = modelMapper.map(unitDTO, Unit.class);
        Unit unitFromDB = unitService.saveUnit(unitFromDTO);
        unitDTO = modelMapper.map(unitFromDB, UnitDTO.class);

        return new ResponseEntity<>(unitDTO, HttpStatus.CREATED);
    }
}
