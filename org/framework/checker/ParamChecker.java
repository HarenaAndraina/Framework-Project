package org.framework.checker;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.framework.annotation.FileParamName;
import org.framework.annotation.Param;
import org.framework.exceptions.ParamException;

import org.framework.session.CustomSession;

public class ParamChecker {
    private List<ParamWithType> paramList = new ArrayList<>();
    // Classe pour encapsuler l'annotation et le type de paramètre
    public static class ParamWithType {
        private Param param;
        private FileParamName paramFile;
        private Class<?> type;

        public ParamWithType(Param param, Class<?> type) {
            this.param = param;
            this.type = type;
        }

        public ParamWithType(FileParamName paramFile, Class<?> type) {
            this.paramFile = paramFile;
            this.type = type;
        }

        public Param getParam() {
            return param;
        }

        public Class<?> getType() {
            return type;
        }
    }
    
    public void getAllMethodParam(Parameter[] parameters) throws ParamException {
        for (Parameter parameter2 : parameters) {
            if (parameter2.isAnnotationPresent(Param.class) ) {
                Param paramAnnotation=parameter2.getAnnotation(Param.class);
                Class<?> paramType=parameter2.getType();
                setParamList(new ParamWithType(paramAnnotation, paramType));
            }
            else if( parameter2.getType().equals(CustomSession.class)) {

            }
            else if (parameter2.isAnnotationPresent(FileParamName.class)) {
                
            }
            else{
                throw new ParamException("ETU002353: il faut ajouter une anotation param sur l'argument");
            }
        }
    }

    public List<ParamWithType> getParamList() {
        return paramList;
    }

    public void setParamList(ParamWithType paramList) {
        this.paramList.add(paramList);
    }

    public void setParamList(List<ParamWithType> paramList) {
        this.paramList = paramList;
    }

    
    
}
