package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.SensorRequestDTO;
import org.soneech.exception.SensorNotCreatedException;
import org.soneech.model.Sensor;
import org.soneech.service.SensorService;
import org.soneech.util.SensorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.soneech.util.ErrorUtils.prepareFieldsErrorMessage;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<Sensor> registerSensor(@RequestBody @Valid SensorRequestDTO sensorRequestDTO,
                                                 BindingResult bindingResult) {
        Sensor sensor = sensorService.convertToSensor(sensorRequestDTO);
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors())
            throw new SensorNotCreatedException(prepareFieldsErrorMessage(bindingResult));

        return ResponseEntity.ok(sensorService.save(sensor));
    }
}
