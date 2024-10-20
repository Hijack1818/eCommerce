package com.eCommerce.eCommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class UserException extends RuntimeException{
	
	public UserException(String message) {
        super(message);
    }
}
