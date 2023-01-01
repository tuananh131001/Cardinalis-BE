package com.cardinalis.userservice.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FailResponseDTO {
    private UserEntityDTO data=null;
    private String code;
    private boolean success;
    private String errors_message;

}