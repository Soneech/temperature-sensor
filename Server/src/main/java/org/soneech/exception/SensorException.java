package org.soneech.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SensorException extends RuntimeException {
    private final HttpStatus status;
    public SensorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
