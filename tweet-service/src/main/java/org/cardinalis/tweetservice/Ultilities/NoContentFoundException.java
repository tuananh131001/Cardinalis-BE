package org.cardinalis.tweetservice.Ultilities;

public class NoContentFoundException extends RuntimeException {
    public NoContentFoundException(String message) {
        super(message);
    }
}