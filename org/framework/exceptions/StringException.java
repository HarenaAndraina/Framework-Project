package org.framework.exceptions;

public class StringException extends Exception {
    private static final int ERROR_CODE = 400;

    public StringException(){
        super();
    }
    public StringException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "StringException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
}
