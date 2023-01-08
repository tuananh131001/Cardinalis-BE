package com.cardinalis.userservice.dao;

import com.cardinalis.userservice.dao.response.AuthUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FailResponseDTO {
    private AuthUserResponse data=null;
    private String code;
    private boolean success;
    private String errors_message;

}