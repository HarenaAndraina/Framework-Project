package org.framework.checker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.annotation.Post;
import org.framework.annotation.RequestMapping;
import org.framework.annotation.security.Auth;
import org.framework.annotation.security.IsGranted;
import org.framework.exceptions.PackageNotFoundException;
import org.framework.exceptions.RequestMappingException;
import org.framework.classSources.ClassFinder;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;


public class RequestMappingChecker {
    private List< Mapping> mappingClasses = new ArrayList<>();

    public void addClassMapping(Mapping map) throws RequestMappingException {
        String normalizedAnnotationValue = normalizeUrl(map.getUrl());

        map.setUrl(normalizedAnnotationValue);

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

    boolean hasRequestMapping(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                return true;
            }
        }
        return false;
    }
    
    public List<Mapping> getRequestMappingMethods(Class<?> clazz) throws RequestMappingException {
        List< Mapping> requestMappingMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                Mapping map=new Mapping(clazz.getName(),method.getName(),requestMapping.value());
                
                if (method.isAnnotationPresent(Post.class)) {
                    map.setPost(true);
                }
                
                if (method.isAnnotationPresent(Auth.class)) {
                    map.setAuth(true);                
                }
                
                if (method.isAnnotationPresent(IsGranted.class)) {
                    IsGranted grantValue=method.getAnnotation(IsGranted.class);
                    map.setGranted(grantValue.value());
                    map.setGrantedSet(true);
                    
                    System.out.println("changer grantedvalue:"+ map.getGranted());
                }
                requestMappingMethods.add(map);
            }
        }
        return requestMappingMethods;
    }

    public void getAllMethodMapping(ServletContext context) throws Exception {
        String packageName = context.getInitParameter("Package");

        List<Class<?>> allClasses = ClassFinder.findClassesController(packageName);

        for (Class<?> clazz : allClasses) {
            if (hasRequestMapping(clazz)) {
                List< Mapping> mappings = getRequestMappingMethods(clazz);

                for (Mapping map : mappings) {
                    addClassMapping(map);
                }
            }
        }
    }

    public List< Mapping> getMappingClasses() {
        return mappingClasses;
    }

    public Mapping getMethodByURL(String url,HttpServletRequest request) throws RequestMappingException
    {
        String normalizedUrl = normalizeUrl(url);
        Mapping map=null;

        for (Mapping mapping : mappingClasses) {
            
            if (mapping.getUrl().equals(normalizedUrl)) {
                map=mapping;
            }
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
