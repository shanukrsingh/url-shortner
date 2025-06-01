package com.author.shortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorEntity> handleException(Exception ex) {
        // Create ErrorEntity dynamically based on exception
        ErrorEntity errorEntity = new ErrorEntity();

        // Dynamically populate fields based on exception type or message
        errorEntity.setErrorType(ex.getClass().getName());
        errorEntity.setErrorMessage(ex.getMessage());

        // Log the error (use any logging framework, e.g., SLF4J)
        System.out.println("Error logged: " + errorEntity.getErrorType() + " - " + errorEntity.getErrorMessage());

        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }
}
