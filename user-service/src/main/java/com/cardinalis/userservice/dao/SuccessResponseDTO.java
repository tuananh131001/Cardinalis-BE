package com.cardinalis.userservice.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
@AllArgsConstructor
public class SuccessResponseDTO {
    private UserEntityDTO data;
    private String code;
    private boolean success;
    private String errors_message;

}