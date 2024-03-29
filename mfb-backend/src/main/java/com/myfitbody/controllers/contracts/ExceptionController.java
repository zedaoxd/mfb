package com.myfitbody.controllers.contracts;

import com.myfitbody.domain.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ExceptionController {

    ResponseEntity<DefaultError> handleDatabaseException(DatabaseException e, HttpServletRequest request);
    ResponseEntity<DefaultError> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request);
    ResponseEntity<ValidationException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request);
    ResponseEntity<DefaultError> handleJWTException(JWTException e, HttpServletRequest request);
    //    ResponseEntity<DefaultError> handleException(Exception e, HttpServletRequest request);
}
