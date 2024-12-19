package org.framework.exceptions;

public class AuthConstraintException extends Exception {
    private static final int ERROR_CODE = 401;

    public AuthConstraintException(){
        super();
    }
    public AuthConstraintException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "AuthConstraintException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
}
