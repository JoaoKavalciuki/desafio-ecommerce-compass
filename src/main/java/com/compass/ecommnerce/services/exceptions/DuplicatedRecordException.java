package com.compass.ecommnerce.services.exceptions;

public class DuplicatedRecordException extends RuntimeException{
    public DuplicatedRecordException(String message) {
        super(message);
    }
}
