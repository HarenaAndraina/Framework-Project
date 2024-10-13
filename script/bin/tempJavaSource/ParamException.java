package org.framework.exceptions;

public class ParamException extends Exception {
    private static final int ERROR_CODE = 500;
    
    public ParamException(){
        super();
    }
    public ParamException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "ParamException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
}
