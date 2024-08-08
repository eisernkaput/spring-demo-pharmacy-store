package com.example.springdemopharmacystore.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class exceptionHandlerController {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException e, WebRequest request) {
        String message = e.getMessage();
        log.error(message);
        request.getDescription(false);
        return new ResponseEntity<>(message + "\n" +request.getDescription(false)
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e, WebRequest request) {
        String message = e.getMessage();
        log.error(message);
        return new ResponseEntity<>(message + "\n" +request.getDescription(false)
                , HttpStatus.I_AM_A_TEAPOT);
    }
}
