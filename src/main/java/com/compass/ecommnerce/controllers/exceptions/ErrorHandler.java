package com.compass.ecommnerce.controllers.exceptions;

import com.compass.ecommnerce.events.exceptions.MailException;
import com.compass.ecommnerce.services.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException exception){
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError(status.value(), "Not Found", errorMessage);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<StandardError> productOutOfStock(ProductOutOfStockException exception){
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(status.value(), "Bad request", errorMessage);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<StandardError> notNullOrNegative(InvalidRequestException exception){
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(status.value(), "Bad request", errorMessage);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ProductAlredyOnSaleException.class)
    public ResponseEntity<StandardError> productOnSale(ProductAlredyOnSaleException exception){
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(status.value(), "Bad request", errorMessage);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DuplicatedRecordException.class)
    public ResponseEntity<StandardError> duplicatedRecord(DuplicatedRecordException exception){
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(status.value(), "Bad request", errorMessage);
        return ResponseEntity.status(status).body(error);
    }
    @ExceptionHandler(MailException.class)
    public ResponseEntity<StandardError> mailException(MailException exception){
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(status.value(), "Bad request", errorMessage);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(EmptySaleException.class)
    public ResponseEntity<StandardError> emptySale(EmptySaleException exception){
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(status.value(), "Bad request", errorMessage);
        return ResponseEntity.status(status).body(error);
    }
    @ExceptionHandler(JWTException.class)
    public ResponseEntity<StandardError> jwtError(EmptySaleException exception){
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError error = new StandardError(status.value(), "Internal Server error", errorMessage);
        return ResponseEntity.status(status).body(error);
    }
}
