package org.framework.checker;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.classSources.ClassFinder;
import org.framework.annotation.RequestMapping;
import org.framework.checker.ControllerChecker;
import org.framework.checker.Mapping;

public class RequestMappingChecker {
    private Map<String, Mapping> mappingClasses = new HashMap<>();

    public void addClassMapping(Mapping map, String annotationValue) {
        String normalizedAnnotationValue = normalizeUrl(annotationValue);
        this.mappingClasses.put(normalizedAnnotationValue, map);
    }

    boolean hasRequestMapping(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, String> getRequestMappingMethods(Class<?> clazz) {
        Map<String, String> requestMappingMethods = new HashMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                requestMappingMethods.put(requestMapping.value(), method.getName());
            }
        }
        return requestMappingMethods;
    }

    public void getAllMethodMapping(String packageName) throws Exception {
        List<Class<?>> allClasses = ClassFinder.findClassesController(packageName);

        for (Class<?> clazz : allClasses) {
            if (hasRequestMapping(clazz)) {
                Map<String, String> mappings = getRequestMappingMethods(clazz);

                for (Map.Entry<String, String> entry : mappings.entrySet()) {
                    addClassMapping(new Mapping(clazz.getName(), entry.getValue()), entry.getKey());
                }
            }
        }
    }

    public Map<String, Mapping> getMappingClasses() {
        return mappingClasses;
    }

    public Mapping getMethodByURL(String url) {
        String normalizedUrl = normalizeUrl(url);
        return mappingClasses.getOrDefault(normalizedUrl, null);
    }

    private String normalizeUrl(String url) {
        if (url.startsWith("/")) {
            url = url.substring(1);
        }
        return url;
    }
}
