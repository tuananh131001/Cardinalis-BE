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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

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
    public ResponseEntity<ResponseDTO> createUser(@RequestBody RegisterDTO register) {
        UserEntity userCreated = userService.save(register);
        return ResponseEntity.created(URI.create("/" + userCreated.getId()))
                .body(ResponseDTO.builder()
                        .data(mapper.map(userCreated, UserEntityDTO.class))
                        .build());
    }
    @GetMapping("/fetch/{username}")
    public ResponseEntity<ResponseDTO> fetchByUsername(@PathVariable("username") String username) {
        UserEntity user = userService.fetchByUsername(username);
        return ResponseEntity.ok(ResponseDTO.builder()
                .data(mapper.map(user, UserEntityDTO.class))
                .build());
    }
    //    Will refactor this later
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginDTO form) {
        UsernamePasswordAuthenticationToken login = form.converter();

        try {
            Authentication authentication = authManager.authenticate(login);
            String token = tokenService.generateToken(authentication);

            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException e) {
            // return "Login Failed: Your user ID or password is incorrect" with 401
            return ResponseEntity.status(401).build();
        }
    }
    @GetMapping
    public String test() {
        return "test";
    }
}
