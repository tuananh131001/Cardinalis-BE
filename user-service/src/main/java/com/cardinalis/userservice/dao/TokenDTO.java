package com.cardinalis.userservice.dao;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class TokenDTO{
    private final String token;
    private final String bearer;
}