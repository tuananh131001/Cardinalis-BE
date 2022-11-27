package com.cardinalis.authservice.dao;


import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}

