package org.framework.checker.validation.clazz;

import java.lang.reflect.Field;

import org.framework.checker.validation.interf.ValidatorInterface;
import org.framework.exceptions.ValidationException;
import org.framework.exceptions.InvalidException;

public class InvalidValidator implements ValidatorInterface {

    @Override
    public void validate(Field field, Object value) throws ValidationException {
        try {
            // Rendre le champ accessible s'il est privé
            field.setAccessible(true);

            // Obtenir la valeur du champ depuis l'objet donné
            Object fieldValue = field.get(value);
            // Compare the field's value with the expected value
            if (fieldValue == null && fieldValue.equals(value)) {
                throw new ValidationException("The value of field '" + field.getName() + "' is invalid: " + fieldValue);
            }
        } catch (Exception e) {
            throw new ValidationException("some error excepting: "+e.getMessage());
        }

    }
}
