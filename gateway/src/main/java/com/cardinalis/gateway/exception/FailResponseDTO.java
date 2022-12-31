package com.cardinalis.gateway.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FailResponseDTO {
    private Object data=null;
    private String code;
    private boolean success;
    private String errors_message;

}