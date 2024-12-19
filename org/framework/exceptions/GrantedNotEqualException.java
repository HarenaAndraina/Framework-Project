package org.framework.exceptions;

public class GrantedNotEqualException extends Exception {
    private static final int ERROR_CODE = 403;
    public GrantedNotEqualException(){
        super();
    }
    public GrantedNotEqualException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "GrantedNotEqualException{" +
                "errorCode=" + ERROR_CODE +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
