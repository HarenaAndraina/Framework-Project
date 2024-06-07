package org.framework.exceptions;

public class RequestMappingException extends Exception {
    public RequestMappingException(){
        super();
    }
    public RequestMappingException(String message){
        super(message);
    }
}
