package org.framework.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.framework.annotation.FieldParamName;
import org.framework.checker.validation.interf.ValidatorInterface;
import org.framework.checker.validation.registry.ValidatorRegistry;
import org.framework.exceptions.ValidationException;

import jakarta.servlet.http.HttpServletRequest;

public class Validator {
     Map<String, String> errors = new HashMap<>();
         Map<String, Object> oldValue = new HashMap<>();
            
            public  void validate(Field[] fields, HttpServletRequest request, Object paramObject,
                    String paramName)
                    throws IllegalAccessException {
               
        
                for (Field field : fields) {
                    field.setAccessible(true);
        
                    String fieldName = getFieldParamName(field);
                    if (fieldName == null)
                        continue; // Skip fields without FieldParamName annotation
        
                    String fullParamName = paramName + "." + fieldName;
                    String paramValue = request.getParameter(fullParamName);
        
                    if (paramValue != null) {
                        try {
                            Object convertedValue = convertToFieldType(field, paramValue);
                            field.set(paramObject, convertedValue);
                            oldValue.put(fullParamName, convertedValue);
                            
                            // Validate using appropriate validator
                            for (Annotation annotation : field.getAnnotations()) {
                                ValidatorInterface validator = ValidatorRegistry.getValidator(annotation.annotationType());
        
                                if (validator != null) {
                                    try {
                                        validator.validate(field, convertedValue);
                                } catch (ValidationException e) {
                                    errors.put(fullParamName, e.getMessage());
                            }
                        }
                    }
                } catch (Exception e) {
                    errors.put(fullParamName, "Invalid value for field " + fieldName + ": " + e.getMessage());
                }
            }
        }

    }

    private  String getFieldParamName(Field field) {
        if (field.isAnnotationPresent(FieldParamName.class)) {
            FieldParamName annotation = field.getAnnotation(FieldParamName.class);
            return annotation.value();
        }
        return null;
    }

    private  Object convertToFieldType(Field field, String value) throws Exception {
        Class<?> fieldType = field.getType();

        try {
            if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                return Integer.parseInt(value);
            } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                return Long.parseLong(value);
            } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
                return Float.parseFloat(value);
            } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                return Double.parseDouble(value);
            } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                return Boolean.parseBoolean(value);
            } else if (fieldType.equals(String.class)) {
                return value;
            } else {
                throw new IllegalArgumentException("Unsupported field type: " + fieldType);
            }
        } catch (Exception e) {
            throw new Exception("Failed to convert value to " + fieldType.getSimpleName() + ": " + e.getMessage(), e);
        }
    }


    public  Map<String, String> getErrors() {
        return errors;
    }

    public  Map<String, Object> getOldValue() {
        return oldValue;
    }

}