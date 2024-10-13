package org.framework.exceptions;

public class InvocationMethodException extends Exception {
    private static final int ERROR_CODE = 500;
    public InvocationMethodException(){
        super();
    }
    public InvocationMethodException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "InvocationMethodException{" +
                "errorCode=" + ERROR_CODE +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
