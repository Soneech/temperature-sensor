package org.soneech.service;

import org.soneech.exception.SensorException;
import org.soneech.model.Sensor;
import org.soneech.repository.MeasurementRepository;
import org.soneech.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;
    private final MeasurementRepository measurementRepository;


    @Autowired
    public SensorService(SensorRepository sensorRepository, MeasurementRepository measurementRepository) {
        this.sensorRepository = sensorRepository;
        this.measurementRepository = measurementRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findById(Long id) {
        Optional<Sensor> foundSensor = sensorRepository.findById(id);
        if (foundSensor.isEmpty())
            throw new SensorException("сенсор с таким id не зарегистрирован");

        return foundSensor.get();
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }
}
