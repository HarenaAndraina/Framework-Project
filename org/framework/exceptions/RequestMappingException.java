package org.framework.exceptions;

public class RequestMappingException extends Exception {
    private static final int ERROR_CODE = 500;

    public RequestMappingException(){
        super();
    }
    public RequestMappingException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "RequestMappingException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
}
