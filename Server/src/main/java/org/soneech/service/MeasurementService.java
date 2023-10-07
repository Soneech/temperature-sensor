package org.soneech.service;

import org.soneech.exception.MeasurementException;
import org.soneech.model.Measurement;
import org.soneech.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public Measurement findById(Long id) {
        Optional<Measurement> foundMeasurement = measurementRepository.findById(id);
        if (foundMeasurement.isEmpty())
            throw new MeasurementException("измерение с таким id не найдено");

        return foundMeasurement.get();
    }

    public Measurement save(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
        return measurementRepository.save(measurement);
    }
}
