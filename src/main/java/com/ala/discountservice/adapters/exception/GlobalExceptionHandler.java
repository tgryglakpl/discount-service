package com.ala.discountservice.adapters.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<String> handleEntityNotFoundException(MethodArgumentTypeMismatchException ex) {
        var headers = new HttpHeaders();
        log.error(ex.getMessage());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public final ResponseEntity<String> handleEntityNotFoundException(HandlerMethodValidationException ex) {
        var headers = new HttpHeaders();
        log.error(ex.getMessage());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        var headers = new HttpHeaders();
        log.error(ex.getMessage());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, NOT_FOUND);
    }

    @ExceptionHandler(ObjectRetrievalFailureException.class)
    public final ResponseEntity<String> handleEntityNotFoundException(ObjectRetrievalFailureException ex) {
        var headers = new HttpHeaders();
        log.error(ex.getMessage());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleException(Exception ex) {
        log.error(ex.getMessage());
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, INTERNAL_SERVER_ERROR);
    }
}
