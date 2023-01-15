package org.cardinalis.tweetservice.Util;

public class NoContentFoundException extends RuntimeException {
    public NoContentFoundException(String message) {
        super(message);
    }
}