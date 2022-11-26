package com.cardinalis.userservice.service;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}


