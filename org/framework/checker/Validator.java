package org.framework.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.framework.annotation.FieldParamName;
import org.framework.annotation.validation.Min;
import org.framework.checker.validation.clazz.InvalidValidator;
import org.framework.checker.validation.interf.ValidatorInterface;
import org.framework.checker.validation.registry.ValidatorRegistry;
import org.framework.exceptions.ValidationException;

import jakarta.servlet.http.HttpServletRequest;

public class Validator {
    Map<String, String> errors = new HashMap<>();
    Map<String, Object> oldValue = new HashMap<>();

    public void validate(Field[] fields, HttpServletRequest request, Object paramObject,
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

                    oldValue.put(fullParamName, paramValue);

                    // Validate using appropriate validator
                    for (Annotation annotation : field.getAnnotations()) {
                                               
                        ValidatorInterface validator = ValidatorRegistry.getValidator(annotation);
                        
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


    private String getFieldParamName(Field field) {
        if (field.isAnnotationPresent(FieldParamName.class)) {
            FieldParamName annotation = field.getAnnotation(FieldParamName.class);
            return annotation.value();
        }
        return null;
    }

    private boolean isForeign(Field field){
        return field.getAnnotation(FieldParamName.class).foreign();
    }

    private Object convertToFieldType(Field field, String value) throws Exception {
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
            } else if (fieldType.equals(java.sql.Timestamp.class)) {
                return convertStringToTimestamp(value);
            } else if (isForeign(field)) {
                // Gestion des types personnalisés (comme AvionModel)
                return convertToCustomType(fieldType, value);
            } else {
                throw new IllegalArgumentException("Unsupported field type: " + fieldType);
            }
        } catch (Exception e) {
            throw new Exception("Failed to convert value to " + fieldType.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
    
    private Object convertToCustomType(Class<?> fieldType, String value) throws Exception {
        try {
            // Créer une instance de la classe personnalisée (par exemple, AvionModel)
            Object instance = fieldType.getDeclaredConstructor().newInstance();
    
            // Supposons que la valeur est un ID pour l'objet personnalisé
            Field idField = fieldType.getDeclaredField("id");
            idField.setAccessible(true);
    
            // Convertir la valeur en type approprié pour l'ID (par exemple, int ou long)
            if (idField.getType().equals(int.class) || idField.getType().equals(Integer.class)) {
                idField.set(instance, Integer.parseInt(value));
            } else if (idField.getType().equals(long.class) || idField.getType().equals(Long.class)) {
                idField.set(instance, Long.parseLong(value));
            } else {
                throw new IllegalArgumentException("Unsupported ID type for field: " + idField.getType());
            }
    
            return instance;
        } catch (Exception e) {
            throw new Exception("Failed to convert value to " + fieldType.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    private java.sql.Timestamp convertStringToTimestamp(String value) throws Exception {
        try {
            // Correct format to parse "2025-02-21T15:09"
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            java.util.Date parsedDate = dateFormat.parse(value);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            throw new Exception("Failed to convert value to Timestamp: " + e.getMessage(), e);
        }
    }
    
    public Map<String, String> getErrors() {
        return errors;
    }

    public Map<String, Object> getOldValue() {
        return oldValue;
    }

}