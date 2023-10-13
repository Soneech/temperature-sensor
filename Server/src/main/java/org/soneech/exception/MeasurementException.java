package org.soneech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MeasurementException extends RuntimeException {
    private final HttpStatus status;
    public MeasurementException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
