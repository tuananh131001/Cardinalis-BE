package org.cardinalis.tweetservice.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDataContract<T> {
    private T data;
}
