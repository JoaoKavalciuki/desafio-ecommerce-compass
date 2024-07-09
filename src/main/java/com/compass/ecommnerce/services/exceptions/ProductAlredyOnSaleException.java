package com.compass.ecommnerce.services.exceptions;

public class ProductAlredyOnSaleException extends RuntimeException{
    public ProductAlredyOnSaleException(String message) {
        super(message);
    }
}
