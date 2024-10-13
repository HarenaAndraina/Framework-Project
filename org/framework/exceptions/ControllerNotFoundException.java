package org.framework.exceptions;

public class ControllerNotFoundException extends Exception {
    private static final int ERROR_CODE = 404;

    public ControllerNotFoundException(){
        super();
    }

    public ControllerNotFoundException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "ControllerNotFoundException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
}
