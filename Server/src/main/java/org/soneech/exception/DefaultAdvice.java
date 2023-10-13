package org.soneech.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleSensorException(SensorException exception) {
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleMeasurementException(MeasurementException exception) {
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), exception.getStatus());
    }
}
