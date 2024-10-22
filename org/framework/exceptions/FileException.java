package org.framework.exceptions;

public class FileException extends Exception {
    private static final int ERROR_CODE = 500;
    public FileException(){
        super();
    }
    public FileException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "FileException{" +
                "errorCode=" + ERROR_CODE +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
