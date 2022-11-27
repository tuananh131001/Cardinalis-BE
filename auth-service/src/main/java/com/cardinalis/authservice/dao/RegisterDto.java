package com.cardinalis.authservice.dao;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
}