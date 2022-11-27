package com.cardinalis.userservice.controller;

import com.cardinalis.userservice.service.UserRegistrationRequest;
import com.cardinalis.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public void registerCustomer(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("new customer registration {}", userRegistrationRequest);
        userService.registerCustomer(userRegistrationRequest);
    }
    @GetMapping
    public String test() {
        return "test";
    }
}
