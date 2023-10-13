package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.SensorRequestDTO;
import org.soneech.dto.SensorResponseDTO;
import org.soneech.exception.SensorException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.Sensor;
import org.soneech.service.SensorService;
import org.soneech.util.SensorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.soneech.util.ErrorUtils.prepareFieldsErrorMessage;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    private final DefaultMapper mapper;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, DefaultMapper mapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<SensorResponseDTO>> getAllSensors() {
        return ResponseEntity.ok(sensorService.findAll().stream().map(mapper::convertSensorToDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorResponseDTO> getSensorById(@PathVariable("id") Long id) throws SensorException {
        return ResponseEntity.ok(mapper.convertSensorToDTO(sensorService.findById(id)));
    }

    @PostMapping("/registration")
    public ResponseEntity<Sensor> registerSensor(@RequestBody @Valid SensorRequestDTO sensorRequestDTO,
                                                 BindingResult bindingResult) {
        Sensor sensor = mapper.convertToSensor(sensorRequestDTO);
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors())
            throw new SensorException(prepareFieldsErrorMessage(bindingResult), HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(sensorService.save(sensor));
    }
}
