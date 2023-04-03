package com.mindex.challenge.exception;

public class InvalidEmployeeIDException extends RuntimeException {
    String message;

    @Override
    public String getMessage() {
        return message;
    }

    public InvalidEmployeeIDException(String message) {
        this.message = message;
    }
}
