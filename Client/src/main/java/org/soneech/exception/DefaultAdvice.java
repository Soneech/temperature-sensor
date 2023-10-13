package org.soneech.exception;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler
    public ResponseEntity<Object> handleException(HttpStatusCodeException exception) {
        return  ResponseEntity
                .status(exception.getStatusCode())
                .body(exception.getResponseBodyAs(new ParameterizedTypeReference<Object>() {}));
    }
}
