package org.framework.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.framework.annotation.Param;

public class ParamChecker {
    private List<Param> paramList = new ArrayList<>();

    
    public void getAllMethodParam(Parameter[] parameters) {
        for (Parameter parameter2 : parameters) {
            if (parameter2.isAnnotationPresent(Param.class)) {
                Param parameter=parameter2.getAnnotation(Param.class);
                setParamList(parameter);
            } 
        }
    }

    public List<Param> getParamList() {
        return paramList;
    }

    private void setParamList(Param param) {
        this.paramList.add(param);
    }
    
}
