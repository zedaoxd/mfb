package com.myfitbody.controllers.impl;

import com.myfitbody.controllers.contracts.ExceptionController;
import com.myfitbody.domain.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class ExceptionControllerImpl implements ExceptionController {

    @Override
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<DefaultError> handleDatabaseException(DatabaseException e, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;
        var error = DefaultError.builder()
                .timestamp(Instant.now())
                .status(status)
                .statusCode(status.value())
                .message("Database error")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @Override
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultError> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var error = DefaultError.builder()
                .timestamp(Instant.now())
                .status(status)
                .statusCode(status.value())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        List<FieldMessage> errors = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ValidationException error = new ValidationException();
        error.setTimestamp(Instant.now());
        error.setStatus(status);
        error.setStatusCode(status.value());
        error.setMessage("Validation error");
        error.setPath(request.getRequestURI());
        error.setErrors(errors);

        return ResponseEntity.status(status).body(error);
    }


}
