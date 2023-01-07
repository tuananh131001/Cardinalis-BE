package com.cardinalis.timelineservice.exception;

public class NoContentFoundException extends RuntimeException {
    public NoContentFoundException(String message) {
        super(message);
    }
}