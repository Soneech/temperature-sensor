package org.soneech.mapper;

import org.modelmapper.ModelMapper;
import org.soneech.dto.MeasurementRequestDTO;
import org.soneech.dto.SensorRequestDTO;
import org.soneech.exception.SensorException;
import org.soneech.model.Measurement;
import org.soneech.model.Sensor;
import org.soneech.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DefaultMapper {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    @Autowired
    public DefaultMapper(ModelMapper modelMapper, SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    public Sensor convertToSensor(SensorRequestDTO sensorRequestDTO) {
        return modelMapper.map(sensorRequestDTO, Sensor.class);
    }

    public Measurement convertToMeasurement(MeasurementRequestDTO measurementRequestDTO) throws SensorException {
        Sensor sensor = sensorService.findById(measurementRequestDTO.getSensorId());
        Measurement measurement = modelMapper.map(measurementRequestDTO, Measurement.class);
        measurement.setSensor(sensor);

        return measurement;
    }
}
