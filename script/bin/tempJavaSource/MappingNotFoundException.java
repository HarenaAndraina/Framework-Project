package org.framework.exceptions;

public class MappingNotFoundException extends Exception {
    private static final int ERROR_CODE = 404;

    public MappingNotFoundException() {
        super();
    }

    public MappingNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "MappingNotFoundException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
}
