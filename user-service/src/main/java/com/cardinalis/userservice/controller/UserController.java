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
import org.springframework.dao.DataAccessException;
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

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final ModelMapper mapper;
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody RegisterDTO register) {
        try {
            UserEntity userCreated = userService.save(register);
            UserEntityDTO userDTO = mapper.map(userCreated, UserEntityDTO.class);

            Map<String, Object> response = createResponse(
                    HttpStatus.CREATED,
                    userDTO
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (IllegalArgumentException e) {
            // Check if the exception message is "User [username] [email] already exists"
            if (e.getMessage().startsWith("User ") && e.getMessage().endsWith(" already exists")) {
                // Return a response with a CONFLICT status code if the exception message matches
                Map<String, Object> response = createResponse(
                        HttpStatus.CONFLICT,
                        "A user with this username or email already exists"
                );
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(response);
            } else {
                // Handle other instances of IllegalArgumentException
                Map<String, Object> response = createResponse(
                        HttpStatus.BAD_REQUEST,
                        "Invalid input"
                );
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }
        } catch (DataAccessException e) {
            // Handle the exception here
            // This exception could be thrown if there is a problem with the database
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error accessing database"
            );
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        } catch (Exception e) {
            // Catch any other exception that may be thrown
            // You can log the exception or return a response with a different HTTP status code
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred"
            );
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }



    @GetMapping("/fetch/{username}")
    public ResponseEntity<Map<String, Object>> fetchByUsername(@PathVariable("username") String username) {
        UserEntity user = userService.fetchByUsername(username);
        UserEntityDTO userDTO = mapper.map(user, UserEntityDTO.class);

        Map<String, Object> response = createResponse(
                HttpStatus.OK,
                userDTO
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody @Valid LoginDTO form) {
        try {
            UsernamePasswordAuthenticationToken login = form.converter();
            Authentication authentication = authManager.authenticate(login);
            String token = tokenService.generateToken(authentication);

            Map<String, Object> response = createResponse(
                    HttpStatus.OK,
                    new TokenDTO(token, "Bearer")
            );

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // return "Login Failed: Your user ID or password is incorrect" with 401
            Map<String, Object> response = createResponse(
                    HttpStatus.UNAUTHORIZED,
                    null,
                    "Login Failed: Your user ID or password is incorrect"
            );

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }
    @GetMapping
    public String test() {
        return "test";
    }
    private Map<String, Object> createResponse(HttpStatus status, Object data, String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", status.is2xxSuccessful());
        response.put("code", status.value());
        response.put("data", data);
        response.put("errors_message", errorMessage);
        return response;
    }

    private Map<String, Object> createResponse(HttpStatus status, Object data) {
        return createResponse(status, data, null);
    }
}
