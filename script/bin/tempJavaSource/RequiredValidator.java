package org.framework.checker.validation.clazz;


import java.lang.reflect.Field;
import org.framework.checker.validation.interf.ValidatorInterface;
import org.framework.exceptions.RequiredException;

public class RequiredValidator implements ValidatorInterface {

    @Override
    public void validate(Field field, Object value) throws RequiredException {
        if (value == null || value.toString().trim().isEmpty()) {
            throw new RequiredException("The " + field.getName() + " is required.");
        }
    }
}

