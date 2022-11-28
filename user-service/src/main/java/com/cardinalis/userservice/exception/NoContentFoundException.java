package com.cardinalis.userservice.exception;

public class NoContentFoundException extends RuntimeException {
    public NoContentFoundException(String message) {
        super(message);
    }
}