package org.cardinalis.tweetservice.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ResponseDataContract<T> {
    private HttpStatus status;
    private T data;
    private T message;
}
