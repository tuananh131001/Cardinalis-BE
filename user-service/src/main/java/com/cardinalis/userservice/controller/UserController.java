package com.cardinalis.userservice.controller;

import com.cardinalis.userservice.dao.*;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.service.TokenService;
import com.cardinalis.userservice.service.UserRegistrationRequest;
import com.cardinalis.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody RegisterDTO register) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", new ArrayList<>());
        response.put("success", true);
        response.put("errors_message", null);

        UserEntity userCreated = userService.save(register);
        response.put("code", HttpStatus.CREATED.value());
        response.put("data", mapper.map(userCreated, UserEntityDTO.class));

        return ResponseEntity.created(URI.create("/" + userCreated.getId())).body(response);
    }

    @GetMapping("/fetch/{username}")
    public ResponseEntity<Map<String, Object>> fetchByUsername(@PathVariable("username") String username) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", new ArrayList<>());
        response.put("success", true);
        response.put("errors_message", null);

        UserEntity user = userService.fetchByUsername(username);
        response.put("code", HttpStatus.OK.value());
        response.put("data", mapper.map(user, UserEntityDTO.class));

        return ResponseEntity.ok(response);
    }

    //    Will refactor this later
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody @Valid LoginDTO form) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", new ArrayList<>());
        response.put("success", true);
        response.put("errors_message", null);

        try {
            UsernamePasswordAuthenticationToken login = form.converter();
            Authentication authentication = authManager.authenticate(login);
            String token = tokenService.generateToken(authentication);

            response.put("code", HttpStatus.OK.value());
            response.put("data", new TokenDTO(token, "Bearer"));

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // return "Login Failed: Your user ID or password is incorrect" with 401
            response.put("code", HttpStatus.UNAUTHORIZED.value());
            response.put("success", false);
            response.put("errors_message", "Login Failed: Your user ID or password is incorrect");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping
    public String test() {
        return "test";
    }
}
