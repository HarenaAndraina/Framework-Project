package org.framework.exceptions;

public class RequiredException extends Exception {
    private static final int ERROR_CODE = 400;

    public RequiredException(){
        super();
    }
    public RequiredException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "RequiredException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
}
