package org.framework.checker;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.annotation.RestAPI;
import org.framework.classSources.ClassFinder;
import org.framework.exceptions.RequestMappingException;
import org.framework.exceptions.PackageNotFoundException;


import jakarta.servlet.ServletContext;


public class RestAPIChecker {
    private Map<String,Mapping> mappingClasses=new HashMap<>();

    public void addClassMapping(Mapping map,String annotationValue) throws RequestMappingException{
        String normalizeAnnotationValue=normalizeUrl(annotationValue);
    
        if(mappingClasses.containsKey(normalizeAnnotationValue)){
            throw new RequestMappingException("Duplicate URL detected for annotation value: " + annotationValue);
        }

        this.mappingClasses.put(normalizeAnnotationValue, map);
    }

    boolean hasRestAPIMapping(Class<?> clazz){
        for (Method method : clazz.getDeclaredMethods() ) {
            if (method.isAnnotationPresent(RestAPI.class)) {
                return true;
            }
        }
        return false;
    }

    public Map<String,String> getRestAPIMethods(Class<?> clazz){
        Map<String,String>  restAPIMethods=new HashMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RestAPI.class)) {
                RestAPI restAPI=method.getAnnotation(RestAPI.class);
                restAPIMethods.put(restAPI.value(),method.getName());
            }
        }
        return restAPIMethods;
    }

    public void getAllMethodMapping(ServletContext context) throws
     Exception{
        String packageName=context.getInitParameter("Package");
        List<Class<?>>allClasses=ClassFinder.findClassesController(packageName);

        for (Class<?> class1 : allClasses) {
            if (hasRestAPIMapping(class1)) {
                Map<String,String> mappings=getRestAPIMethods(class1);

                for (Map.Entry<String,String> entry : mappings.entrySet()) {
                    addClassMapping(new Mapping(class1.getName(), entry.getValue()), entry.getKey());
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
    
    private String normalizeUrl(String url){
        if (url.startsWith("/")) {
            url=url.substring(1);
        }
        return url;
    }
}
