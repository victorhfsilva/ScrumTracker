package com.db.scrumtrackerapi.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.db.scrumtrackerapi.exceptions.BadEmailException;
import com.db.scrumtrackerapi.exceptions.BadPasswordException;
import com.db.scrumtrackerapi.exceptions.EntityAlreadyExistsException;
import com.db.scrumtrackerapi.exceptions.EntityNotFoundException;
import com.db.scrumtrackerapi.model.view.ErrorMessageView;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * Handles validation exceptions.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing an error message and HTTP status code.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessageView> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorMessageView response = new ErrorMessageView("Validation failed.", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handles token expiration exceptions.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing an error message and HTTP status code.
     */
    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessageView> handleExpiredTokensException(TokenExpiredException ex) {
        ErrorMessageView response = new ErrorMessageView("This token has expired.", HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    
    /**
     * Handles entity already exists exceptions.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing an error message and HTTP status code.
     */
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessageView> handleEntityExistsException(EntityAlreadyExistsException ex) {      
        ErrorMessageView response = new ErrorMessageView("Entity already exists on the database.", HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Handles entity not found exceptions.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing an error message and HTTP status code.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessageView> handleEntityNotFoundException(EntityNotFoundException ex) {      
        ErrorMessageView response = new ErrorMessageView("Entity not found in the database.", HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Handles bad password exceptions.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing an error message and HTTP status code.
     */
    @ExceptionHandler(BadPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessageView> handleAuthenticationException(BadPasswordException ex) {
        ErrorMessageView response = new ErrorMessageView("Authentication failed", HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handles bad email exceptions.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing an error message and HTTP status code.
     */
    @ExceptionHandler(BadEmailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessageView> handleUserNameNotFoundExcepiton(BadEmailException ex) {
        ErrorMessageView response = new ErrorMessageView("Email wasn't registered in the database.", HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles generic exceptions.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing an error message and HTTP status code.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessageView> serverError(Exception ex) {
        ErrorMessageView response = new ErrorMessageView("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

