package com.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UploadExceptions.class)
    public ResponseEntity<String> handle(UploadExceptions ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getError().getMessage());
    }

    @ExceptionHandler(ExportExceptions.class)
    public ResponseEntity<String> handle(ExportExceptions ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getError().getMessage());
    }

    @ExceptionHandler(EntityByIdNotFoundException.class)
    public ResponseEntity<String> handle(EntityByIdNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
