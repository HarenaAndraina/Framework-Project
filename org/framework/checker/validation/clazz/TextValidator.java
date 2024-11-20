package org.framework.checker.validation.clazz;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import org.framework.annotation.validation.Text;
import org.framework.exceptions.TextException;
import org.framework.checker.validation.interf.ValidatorInterface;


public class TextValidator implements ValidatorInterface {
     @Override
    public void validate(Field field, Object value) throws TextException {
        if (value == null || !(value instanceof String)) {
            throw new TextException("The " + field.getName() + " must be a string.");
        }

        Text annotation = field.getAnnotation(Text.class);
        String stringValue = (String) value;

        // Example validation logic
        if (!Pattern.matches("^[a-zA-Z]+$", stringValue)) {
            throw new TextException("The " + field.getName() + " must contain only alphabetic characters.");
        }
    }
}
