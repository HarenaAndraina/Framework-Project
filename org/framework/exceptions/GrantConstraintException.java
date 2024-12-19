package org.framework.exceptions;

public class GrantConstraintException extends Exception{
    private static final int ERROR_CODE = 403;
    public GrantConstraintException(){
        super();
    }
    public GrantConstraintException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "GrantConstraintException{" +
                "errorCode=" + ERROR_CODE +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
