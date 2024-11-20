package org.framework.checker.validation.registry;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.framework.checker.validation.clazz.RequiredValidator;
import org.framework.checker.validation.clazz.TextValidator;
import org.framework.checker.validation.interf.ValidatorInterface;

public class ValidatorRegistry {
    private static final Map<Class<? extends Annotation>, ValidatorInterface> registry = new HashMap<>();

    static {
        registry.put(org.framework.annotation.validation.Required.class, new RequiredValidator());
        registry.put(org.framework.annotation.validation.Text.class, new TextValidator());
    }

    public static ValidatorInterface getValidator(Class<? extends Annotation> annotationType) {
        return registry.get(annotationType);
    }
}
