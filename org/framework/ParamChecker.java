package org.framework.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.framework.annotation.Param;

public class ParamChecker {
    private List<ParamWithType> paramList = new ArrayList<>();
    
    // Classe pour encapsuler l'annotation et le type de paramètre
    public static class ParamWithType {
        private Param param;
        private Class<?> type;

        public ParamWithType(Param param, Class<?> type) {
            this.param = param;
            this.type = type;
        }

        public Param getParam() {
            return param;
        }

        public Class<?> getType() {
            return type;
        }
    }
    
    public void getAllMethodParam(Parameter[] parameters) {
        for (Parameter parameter2 : parameters) {
            if (parameter2.isAnnotationPresent(Param.class)) {
                Param paramAnnotation=parameter2.getAnnotation(Param.class);
                Class<?> paramType=parameter2.getType();
                setParamList(new ParamWithType(paramAnnotation, paramType));
            } 
        }
    }

    public List<ParamWithType> getParamList() {
        return paramList;
    }

    public void setParamList(ParamWithType paramList) {
        this.paramList.add(paramList);
    }
    
}
