package com.db.scrumtrackerapi.exceptions;

public class BadEmailException extends RuntimeException {
    
    public BadEmailException(String message) {
        super(message);
    }

}
