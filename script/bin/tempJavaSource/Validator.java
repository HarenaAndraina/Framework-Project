package org.framework.checker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.framework.annotation.validation.NotNull;
import org.framework.annotation.validation.Required;

import org.framework.exceptions.RequiredException;
import org.framework.exceptions.NotNullException;

public class Validator {

    public static Map<String, String> validate(Object paramObject) throws IllegalAccessException {
        Map<String, String> errors = new HashMap<>();

        for (Field field : paramObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(paramObject);

            try {
                // Check for @Required
                if (field.isAnnotationPresent(Required.class)) {
                    validateRequired(field, value);
                }

                // Check for @NotNull
                if (field.isAnnotationPresent(NotNull.class)) {
                    validateNotNull(field, value);
                }

            } catch (RequiredException | NotNullException e) {
                // Capture the specific exception message in the errors map
                errors.put(field.getName(), e.getMessage());
            }
        }

        return errors;
    }

    private static void validateRequired(Field field, Object value) throws RequiredException {
        if (value == null || value.toString().trim().isEmpty()) {
            throw new RequiredException(" The "+ field.getName()+" is required");
        }
    }

    private static void validateNotNull(Field field, Object value) throws NotNullException {
        if (value == null) {
            throw new NotNullException(field.getName());
        }
    }
}