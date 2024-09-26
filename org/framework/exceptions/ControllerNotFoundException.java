package org.framework.exceptions;

public class ControllerNotFoundException extends Exception {
    public ControllerNotFoundException(){
        super();
    }

    public ControllerNotFoundException(String message){
        super(message);
    }
}
