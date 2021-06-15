package com.epam.esm.web.exception;

public class ResponseError {
    private final String errorMessage;
    private final int errorCode;

    public ResponseError(String message, int code) {
        this.errorMessage = message;
        this.errorCode = code;
    }

    public String getMessage() {
        return this.errorMessage;
    }

    public int getCode() {
        return this.errorCode;
    }

}
