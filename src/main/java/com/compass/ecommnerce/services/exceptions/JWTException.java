package com.compass.ecommnerce.services.exceptions;

public class JWTException extends RuntimeException{

    public JWTException(String message) {
        super(message);
    }
}
