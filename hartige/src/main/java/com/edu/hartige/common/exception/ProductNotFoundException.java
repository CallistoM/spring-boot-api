package com.edu.hartige.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The product could not be found")
public class ProductNotFoundException extends Exception {
    private static final long serialVersionUID = 100L;
    private String message;

    public ProductNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}