package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.MeasurementRequestDTO;
import org.soneech.dto.MeasurementResponseDTO;
import org.soneech.exception.MeasurementException;
import org.soneech.exception.SensorException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.Measurement;
import org.soneech.service.MeasurementService;
import org.soneech.util.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.soneech.util.ErrorUtils.prepareFieldsErrorMessage;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final DefaultMapper mapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, DefaultMapper mapper) {
        this.measurementService = measurementService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<MeasurementResponseDTO>> getAllMeasurements() {
        return ResponseEntity.ok(
                measurementService.findAll().stream()
                        .map(mapper::convertMeasurementToDTO).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementResponseDTO> getMeasurementById(@PathVariable("id") Long id)
            throws MeasurementException {
        return ResponseEntity.ok(mapper.convertMeasurementToDTO(measurementService.findById(id)));
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Map<String, Integer>> getRainyDaysCount() {
        return ResponseEntity.ok(Map.of("rainy_days", measurementService.getRainyDaysCount()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveMeasurement(@RequestBody @Valid MeasurementRequestDTO measurementRequestDTO,
                                                       BindingResult bindingResult) throws SensorException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(Map.of("message", prepareFieldsErrorMessage(bindingResult)));

        Measurement measurement = mapper.convertToMeasurement(measurementRequestDTO);
        return ResponseEntity.ok(measurementService.save(measurement));
    }

    @PostMapping("/addAll")
    public ResponseEntity<?> saveAllMeasurements(@RequestBody @Valid ValidList<MeasurementRequestDTO> measurementsRequestDTO,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(Map.of("message", prepareFieldsErrorMessage(bindingResult)));

        List<Measurement> measurements = measurementsRequestDTO.stream().map(mapper::convertToMeasurement).toList();
        measurementService.saveAll(measurements);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
