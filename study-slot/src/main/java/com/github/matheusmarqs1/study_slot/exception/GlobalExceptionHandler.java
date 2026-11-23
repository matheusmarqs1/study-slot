package com.github.matheusmarqs1.study_slot.exception;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(ErrorResponse.of(status, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ErrorResponse.FieldError> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> new ErrorResponse.FieldError(e.getField(), e.getDefaultMessage()))
            .toList();

        return ResponseEntity.status(status).body(ErrorResponse.of(status, "Validation failed", request.getRequestURI(), errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(ErrorResponse.of(status, "Malformed JSON request", request.getRequestURI()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(ErrorResponse.of(status, "Data integrity violation", request.getRequestURI()));
    }

    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<ErrorResponse> handleReservationConflict(ReservationConflictException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(ErrorResponse.of(status, ex.getMessage(), request.getRequestURI()));
    }

}
