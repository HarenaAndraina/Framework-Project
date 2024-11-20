package org.framework.checker.validation.interf;

import java.lang.reflect.Field;

import org.framework.exceptions.ValidationException;

public interface ValidatorInterface {
    void validate(Field field, Object value) throws ValidationException;
} 