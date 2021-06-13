package com.epam.esm.exception;

public class ResourceExistenceException extends ApplicationException{
    public ResourceExistenceException() {
    }

    public ResourceExistenceException(String message) {
        super(message);
    }

    public ResourceExistenceException(String message, int errorCode) {
        super(message, errorCode);
    }
}
