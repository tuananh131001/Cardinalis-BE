package com.cardinalis.userservice.controller;

import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.dao.ResponseDTO;
import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.service.UserRegistrationRequest;
import com.cardinalis.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {
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
    @GetMapping
    public String test() {
        return "test";
    }
}
