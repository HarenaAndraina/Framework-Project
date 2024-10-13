package org.framework.exceptions;

/**
 * PackageNotFoundException
 */
public class PackageNotFoundException extends Exception {
    private static final int ERROR_CODE = 404;

    public PackageNotFoundException(){
        super();
    }
    public PackageNotFoundException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "PackageNotFoundException{" +
                "errorCode=" + ERROR_CODE +
                ", message=" + getMessage() +
                '}';
    }
    

    
}