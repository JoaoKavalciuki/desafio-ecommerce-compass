package com.compass.ecommnerce.controllers.exceptions;

import com.compass.ecommnerce.services.exceptions.EmptySaleException;
import com.compass.ecommnerce.services.exceptions.ProductOutOfStockException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
        HttpStatus status = HttpStatus.OK;

        StandardError error = new StandardError(status.value(), "OK", errorMessage);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(EmptySaleException.class)
    public ResponseEntity<StandardError> emptySale(EmptySaleException exception){
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(status.value(), "Bad request", errorMessage);
        return ResponseEntity.status(status).body(error);
    }

}
