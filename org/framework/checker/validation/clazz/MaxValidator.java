package org.framework.checker.validation.clazz;

import java.lang.reflect.Field;

import org.framework.checker.validation.interf.ValidatorInterface;
import org.framework.exceptions.MaxLengthException;
import org.framework.exceptions.ValidationException;

public class MaxValidator implements ValidatorInterface {
    private int maxValue;

    @Override
    public void validate(Field field, Object value) throws ValidationException {
        if (value.toString().length() > getMaxValue() ) {
            throw new MaxLengthException("The "+ field.getName()+" has an invalid length");
        }
        
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    
}
