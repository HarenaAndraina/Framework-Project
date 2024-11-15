package org.framework.checker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.framework.annotation.validation.Text;
import org.framework.annotation.validation.Required;
import org.framework.exceptions.RequiredException;
import org.framework.exceptions.StringException;

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
                if (field.isAnnotationPresent(Text.class)) {
                    validateString(field, value);
                }

            } catch (RequiredException | StringException e) {
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

    private static void validateString(Field field,Object value) throws StringException{
       if (value == null || !(value instanceof java.lang.String)) {
            throw new StringException("The " + field.getName() + " must be a string.");
        }

        Text stringValue = (Text) value;


        // Example: Check if the string contains only alphabetic characters
        if (!Pattern.matches("^[a-zA-Z]+$", (CharSequence) stringValue)) {
            throw new StringException("The " + field.getName() + " must contain only alphabetic characters.");
        }
    }
}