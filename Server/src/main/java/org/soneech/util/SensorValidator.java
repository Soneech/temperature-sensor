package org.soneech.util;

import org.soneech.model.Sensor;
import org.soneech.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        Optional<Sensor> foundSensor = sensorService.findByName(sensor.getName());
        if (foundSensor.isPresent())
            errors.rejectValue("name", "", "сенсор с таким названием уже существует");
    }
}
