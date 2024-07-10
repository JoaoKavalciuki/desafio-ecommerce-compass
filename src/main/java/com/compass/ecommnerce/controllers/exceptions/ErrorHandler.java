package com.compass.ecommnerce.controllers.exceptions;

import com.compass.ecommnerce.services.exceptions.DuplicatedRecordException;
import com.compass.ecommnerce.services.exceptions.InvalidRequestException;
import com.compass.ecommnerce.services.exceptions.ProductAlredyOnSaleException;
import com.compass.ecommnerce.services.exceptions.ProductOutOfStockException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
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
}
