package org.framework.checker.validation.clazz;

import java.lang.reflect.Field;

import org.framework.checker.validation.interf.ValidatorInterface;
import org.framework.exceptions.MinLengthException;
import org.framework.exceptions.ValidationException;

public class MinValidator implements ValidatorInterface {
    private int minValue;
    
    @Override
    public void validate(Field field, Object value) throws ValidationException {
        if (value.toString().length() < getMinValue() ) {
            throw new MinLengthException("The "+ field.getName()+" has an invalid length");
        }
        
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }
    public int getMinValue() {
        return minValue;
    }
    
}
