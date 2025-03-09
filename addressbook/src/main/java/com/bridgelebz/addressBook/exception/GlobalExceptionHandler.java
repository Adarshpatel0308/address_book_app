package com.bridgelebz.addressBook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract validation errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        // Create a user-friendly error response
        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errors);

        // Return the error response with HTTP status 400 (Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException ex) {
        // Create a user-friendly error response
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), List.of());

        // Return the error response with HTTP status 404 (Not Found)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}