package org.framework.exceptions;

public class ValidationException extends Exception {
    public ValidationException(){
        super();
    }
    public ValidationException(String message){
        super(message);
    }
    @Override
    public String toString() {
        return "ValidationException{" +
                
                ", message=" + getMessage() +
                '}';
    }
}
