package org.framework.exceptions;

import java.util.Map;
import java.util.stream.Collectors;

public class ValidationException extends Exception {
     private final Map<String, String> errors;

     public ValidationException(String message, Map<String, String> errors) {
        super(message + ": " + formatErrors(errors));
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
    
    private static String formatErrors(Map<String, String> errors) {
            return errors.entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.joining("\n"));
    }
}
