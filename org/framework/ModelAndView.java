package org.framework.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.framework.checker.Mapping;

/**
 * ModelAndView
 */
public class ModelAndView {

    public static String showMessage(Mapping map) {
        String message = "";
        try {
            String className = map.getClassName();
            String methodName = map.getMethodName();
    
            Class<?> clazz = Class.forName(className);
    
            Method method = clazz.getMethod(methodName);
    
            Object instance = clazz.getDeclaredConstructor().newInstance();
    
            message += (String) method.invoke(instance);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | InvocationTargetException e) {
            message += "url not found";
            e.printStackTrace(); // Optionally, you can print the stack trace for debugging
        }
        return message;
    }
}