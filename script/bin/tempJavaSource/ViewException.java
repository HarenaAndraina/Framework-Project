package org.framework.exceptions;

/**
 * ViewException
 */
public class ViewException extends Exception {
    private static final int ERROR_CODE = 404;
    
    public ViewException(){
        super();
    }
    
    public ViewException(String message){
        super(message);
    }
    @Override
    public String toString() {
        return "ViewException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }

}