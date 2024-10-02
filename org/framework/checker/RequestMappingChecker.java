package org.framework.checker;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.annotation.RequestMapping;
import org.framework.exceptions.PackageNotFoundException;
import org.framework.exceptions.RequestMappingException;
import org.framework.annotation.outil.RequestMethod;
import org.framework.classSources.ClassFinder;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;


public class RequestMappingChecker {
    private Map<String, Mapping> mappingClasses = new HashMap<>();

    public void addClassMapping(Mapping map, String annotationValue) throws RequestMappingException {
        String normalizedAnnotationValue = normalizeUrl(annotationValue);

        if (mappingClasses.containsKey(normalizedAnnotationValue)) {
            throw new RequestMappingException("Duplicate URL detected for annotation value: " + annotationValue);
        }

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
    
    public Map<String, Mapping> getRequestMappingMethods(Class<?> clazz) throws RequestMappingException {
        Map<String, Mapping> requestMappingMethods = new HashMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                
                Mapping map=new Mapping(clazz.getName(),method.getName(), requestMapping.method());
                
                requestMappingMethods.put(requestMapping.value(), map);
            }
        }
        return requestMappingMethods;
    }

    
    public void getAllMethodMapping(ServletContext context) throws Exception {
        String packageName = context.getInitParameter("Package");

        List<Class<?>> allClasses = ClassFinder.findClassesController(packageName);

        for (Class<?> clazz : allClasses) {
            if (hasRequestMapping(clazz)) {
                Map<String, Mapping> mappings = getRequestMappingMethods(clazz);

                for (Map.Entry<String, Mapping> entry : mappings.entrySet()) {
                    addClassMapping(entry.getValue(), entry.getKey());
                }
            }
        }

    }

    public Map<String, Mapping> getMappingClasses() {
        return mappingClasses;
    }

    public Mapping getMethodByURL(String url,HttpServletRequest request) throws RequestMappingException
     {
        String normalizedUrl = normalizeUrl(url);
        Mapping map=mappingClasses.getOrDefault(normalizedUrl, null);
        
        if(!map.getVerbe().name().equalsIgnoreCase(request.getMethod())){
            throw new RequestMappingException("Supported HTTP method for this URL is: " + map.getVerbe().name());
        }
        
        return map;
    }

    private String normalizeUrl(String url) {
        if (url.startsWith("/")) {
            url = url.substring(1);
        }
        return url;
    }
}
