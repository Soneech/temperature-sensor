package org.soneech.service;

import org.soneech.exception.SensorException;
import org.soneech.model.Sensor;
import org.soneech.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findById(Long id) {
        Optional<Sensor> foundSensor = sensorRepository.findById(id);
        if (foundSensor.isEmpty())
            throw new SensorException("сенсор с таким id не зарегистрирован", HttpStatus.NOT_FOUND);

        return foundSensor.get();
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }
}
