package com.workintech.Ecommerce.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ECommerceException extends RuntimeException{
    private HttpStatus status;

    public ECommerceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
