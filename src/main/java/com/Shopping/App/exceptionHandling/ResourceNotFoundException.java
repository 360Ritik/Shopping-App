package com.Shopping.App.exceptionHandling;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message,Long id) {
        super(message+" "+id);
    }
}
