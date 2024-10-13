package org.framework.checker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.annotation.Post;
import org.framework.annotation.RestAPI;
import org.framework.classSources.ClassFinder;
import org.framework.exceptions.RequestMappingException;
import org.framework.exceptions.PackageNotFoundException;


import jakarta.servlet.ServletContext;


public class RestAPIChecker {
    private List<Mapping> mappingClasses=new ArrayList<>();

    public void addClassMapping(Mapping map) throws RequestMappingException{
        String normalizeAnnotationValue=normalizeUrl(map.getUrl());
    
        map.setUrl(normalizeAnnotationValue);
        System.out.println(map.getMethodName());
        System.out.println(map.getUrl());
        System.out.println("aaa");

        for (Mapping mapping : mappingClasses) {
            if (map.getUrl().equals(mapping.getUrl())) {
                throw new RequestMappingException("Duplicate URL at "+map.getClassName()+" class");
            }
            if (map.getMethodName().equals(mapping.getMethodName())) {
                throw new RequestMappingException("Duplicate method at "+map.getClassName()+" class");
            }
        }

        this.mappingClasses.add(map);
    }

    boolean hasRestAPIMapping(Class<?> clazz){
        for (Method method : clazz.getDeclaredMethods() ) {
            if (method.isAnnotationPresent(RestAPI.class)) {
                return true;
            }
        }
        return false;
    }

    public List<Mapping> getRestAPIMethods(Class<?> clazz){
        List<Mapping>  restAPIMethods=new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RestAPI.class)) {
                RestAPI restAPI=method.getAnnotation(RestAPI.class);
                 Mapping map=new Mapping(clazz.getName(),method.getName(),restAPI.value());
                
                if (method.isAnnotationPresent(Post.class)) {
                    map.setPost(true);
                }
                restAPIMethods.add(map);
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
                List<Mapping> mappings=getRestAPIMethods(class1);

                for (Mapping map : mappings) {
                    addClassMapping(map);
                }
            }
        }
    }

    public List< Mapping> getMappingClasses() {
        return mappingClasses;
    }

    public Mapping getMethodByURL(String url) {
        String normalizedUrl = normalizeUrl(url);
        Mapping map=null;
        for (Mapping mapping : mappingClasses) {
            if (mapping.getUrl().equals(normalizedUrl)) {
                map=mapping;
            }
        }
        return map;
    }
    
    private String normalizeUrl(String url){
        if (url.startsWith("/")) {
            url=url.substring(1);
        }
        return url;
    }
}
