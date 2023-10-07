package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.MeasurementRequestDTO;
import org.soneech.exception.SensorException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.Measurement;
import org.soneech.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/add")
    public ResponseEntity<?> saveMeasurement(@RequestBody @Valid MeasurementRequestDTO measurementRequestDTO,
                                                       BindingResult bindingResult) throws SensorException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(Map.of("message", prepareFieldsErrorMessage(bindingResult)));

        Measurement measurement = mapper.convertToMeasurement(measurementRequestDTO);
        return ResponseEntity.ok(measurementService.save(measurement));
    }
}
