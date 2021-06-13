package com.epam.esm.web.exception;

public class ResponseError {
    private String errorMessage;
    private int errorCode;

    public ResponseError(String message, int code) {
        this.errorMessage = message;
        this.errorCode = code;
    }

    public String getMessage() {
        return errorMessage;
    }

    public int getCode() {
        return errorCode;
    }

}
