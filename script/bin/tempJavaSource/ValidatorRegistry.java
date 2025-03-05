package org.framework.checker.validation.registry;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.framework.annotation.validation.Max;
import org.framework.annotation.validation.Min;
import org.framework.checker.validation.clazz.InvalidValidator;
import org.framework.checker.validation.clazz.MaxValidator;
import org.framework.checker.validation.clazz.MinValidator;
import org.framework.checker.validation.clazz.RequiredValidator;
import org.framework.checker.validation.clazz.TextValidator;
import org.framework.checker.validation.interf.ValidatorInterface;

public class ValidatorRegistry {
    private static final Map<Class<? extends Annotation>, ValidatorInterface> registry = new HashMap<>();

    static {
        registry.put(org.framework.annotation.validation.Required.class, new RequiredValidator());
        registry.put(org.framework.annotation.validation.Text.class, new TextValidator());
        registry.put(org.framework.annotation.validation.Invalid.class, new InvalidValidator());
        registry.put(org.framework.annotation.validation.Max.class, new MaxValidator());
        registry.put(org.framework.annotation.validation.Min.class, new MinValidator());
    }

    public static ValidatorInterface getValidator(Annotation annotationInstance) {
        Class<? extends Annotation> annotationType = annotationInstance.annotationType();
        ValidatorInterface validator = registry.get(annotationType);
        if (validator != null) {
            try {

                if (validator instanceof MaxValidator && annotationInstance instanceof Max) {
                    Max max = (Max) annotationInstance;
                    MaxValidator maxValidator = (MaxValidator) validator;
                    maxValidator.setMaxValue(Integer.parseInt(max.value()));
                } else if (validator instanceof MinValidator && annotationInstance instanceof Min) {
                    Min min = (Min) annotationInstance;
                    MinValidator minValidator = (MinValidator) validator;
                    minValidator.setMinValue(Integer.parseInt(min.value()));
                }

            } catch (Exception e) {
                throw new RuntimeException("Could not instantiate validator for " + annotationType, e);
            }
        }
        return validator;
    }
}
