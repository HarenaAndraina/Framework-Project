package org.framework.exceptions;

public class NotNullException extends Exception {
    private static final int ERROR_CODE = 400;

    public NotNullException(){
        super();
    }
    public NotNullException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "NotNullException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
}
