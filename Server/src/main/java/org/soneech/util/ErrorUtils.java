package org.soneech.util;

import org.springframework.validation.BindingResult;

public class ErrorUtils {
    public static String prepareFieldsErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (var error: bindingResult.getFieldErrors()) {
            errorMessage
                    .append(error.getField()).append(": ")
                    .append(error.getDefaultMessage()).append(";");
        }
        return errorMessage.toString();
    }
}
