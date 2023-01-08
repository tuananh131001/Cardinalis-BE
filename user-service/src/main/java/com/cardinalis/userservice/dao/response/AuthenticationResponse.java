package com.cardinalis.userservice.dao.response;

import com.cardinalis.userservice.dao.SuccessResponseDTO;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private AuthUserResponse user;

    private String token;

}