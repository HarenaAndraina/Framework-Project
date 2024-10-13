package org.framework.exceptions;

public class MethodVerbException  extends Exception{
    private static final int ERROR_CODE = 500;

    public MethodVerbException(){
        super();
    }
    public MethodVerbException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "MethodVerbException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
}
