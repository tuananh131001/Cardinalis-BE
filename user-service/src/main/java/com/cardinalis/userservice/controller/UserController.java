package com.cardinalis.userservice.controller;

import com.cardinalis.userservice.dao.*;
import com.cardinalis.userservice.dao.request.AuthenticationRequest;
import com.cardinalis.userservice.dao.request.ChangePasswordDTO;
import com.cardinalis.userservice.dao.response.AuthUserResponse;
import com.cardinalis.userservice.dao.response.AuthenticationResponse;
import com.cardinalis.userservice.dao.response.FollowerUserResponse;
import com.cardinalis.userservice.dao.response.notification.NotificationResponse;
import com.cardinalis.userservice.dao.response.notification.NotificationUserResponse;
import com.cardinalis.userservice.exception.ApiRequestException;
import com.cardinalis.userservice.mapper.UserMapper;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final AuthenticationManager authManager;
    private final UserService userService;
    private final ModelMapper mapper;
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody RegisterDTO register) {
        if (register.getPassword().length() < 8) {
            throw new ApiRequestException("Your password needs to be at least 8 characters", HttpStatus.BAD_REQUEST);
        }
        try {
            Map<String, Object> userCreated = userService.save(register);
            Map<String, Object> response = createResponse(
                    HttpStatus.CREATED,
                    userCreated
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
                        null,
                        "A user with this username already exists"

                );
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(response);
            } else if(e.getMessage().startsWith("Email ") && e.getMessage().endsWith(" already exists")){
                // Return a response with a BAD_REQUEST status code if the exception message matches
                Map<String, Object> response = createResponse(
                        HttpStatus.BAD_REQUEST,
                        null,
                        "Email already exists"

                );
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response);
            } else {
                // Handle other instances of IllegalArgumentException
                Map<String, Object> response = createResponse(
                        HttpStatus.BAD_REQUEST,
                        null,
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
                    null,
                    e.getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        } catch (Exception e) {
            // Catch any other exception that may be thrown
            // You can log the exception or return a response with a different HTTP status code
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "An error occurred"
            );
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
    @GetMapping("/followers")
    public ResponseEntity<SuccessResponseDTO> getFollowers(@RequestParam(name = "userId", defaultValue = "") Long userId, @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = userMapper.getFollowers(userId, pageable);
        SuccessResponseDTO successResponseDTO = SuccessResponseDTO.builder()
                .data(response.getItems())
                .code("200")
                .success(true)
                .build();
        return ResponseEntity.ok().headers(response.getHeaders()).body(successResponseDTO);
    }

    @GetMapping("/following")
    public ResponseEntity<SuccessResponseDTO> getFollowing(@RequestParam(name = "userId", defaultValue = "") Long userId, @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = userMapper.getFollowing(userId, pageable);
        SuccessResponseDTO successResponseDTO = SuccessResponseDTO.builder()
                .data(response.getItems())
                .code("200")
                .success(true)
                .build();
        return ResponseEntity.ok().headers(response.getHeaders()).body(successResponseDTO);
    }



    @GetMapping("/follow")
    public ResponseEntity<SuccessResponseDTO> processFollow(@RequestParam(name = "userId", defaultValue = "") Long userId) {
        Map<String,Object> notification = userMapper.processFollow(userId);

//        if (notification.getId() != null) {
//            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getUserToFollow().getId(), notification);
//        }
        SuccessResponseDTO successResponseDTO = SuccessResponseDTO.builder()
                .data(notification)
                .code("200")
                .success(true)
                .build();
        return ResponseEntity.ok(successResponseDTO);
    }



    @PutMapping("")
    public ResponseEntity<Object> updateUser(
                                     @RequestBody AuthUserResponse requestDTO) {
        try {
            UserEntity user = userService.updateUser( requestDTO);
            return ResponseEntity.ok(SuccessResponseDTO.builder()
                    .data(mapper.map(user, AuthUserResponse.class))
                    .code("200")
                    .success(true)
                    .build());
        } catch ( Exception e) {
        return ResponseEntity.badRequest().body(FailResponseDTO.builder()
                            .data(null)
                            .code("400")
                            .success(false)
                            .errors_message("Error : " + e.getMessage())
                            .build());
        }
    }
    //change password
    @PutMapping("/change-password")
    public ResponseEntity<Object> changePassword(
            @RequestBody ChangePasswordDTO requestDTO) {
        try {
            Map<String, Object> user = userService.changePassword(requestDTO);
            return ResponseEntity.ok(SuccessResponseDTO.builder()
                    .data(mapper.map(user, AuthUserResponse.class))
                    .code("200")
                    .success(true)
                    .build());
        } catch ( Exception e) {
            return ResponseEntity.badRequest().body(FailResponseDTO.builder()
                    .data(null)
                    .code("400")
                    .success(false)
                    .errors_message("Error : " + e.getMessage())
                    .build());
        }
    }

    @GetMapping("/fetch/email={email}")
    public ResponseEntity<Map<String, Object>> fetchByEmail(@PathVariable("email") String email) {
        UserEntity user = userService.fetchByEmail(email);
        AuthUserResponse userDTO = mapper.map(user, AuthUserResponse.class);

        Map<String, Object> response = createResponse(
                HttpStatus.OK,
                userDTO
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetch/{username}")
    public ResponseEntity<Map<String, Object>> fetchByUsername(@PathVariable("username") String username) {
        UserEntity user = userService.fetchByUsername(username);
        AuthUserResponse userDTO = mapper.map(user, AuthUserResponse.class);

        Map<String, Object> response = createResponse(
                HttpStatus.OK,
                userDTO
        );

        return ResponseEntity.ok(response);
    }
//    @PostMapping("/logout")
//    public ResponseEntity<String> revoke(HttpServletRequest request) {
//        try {
//            String authorization = request.getHeader("Authorization");
//            if (authorization != null && authorization.contains("Bearer")) {
//                String tokenValue = authorization.replace("Bearer", "").trim();
//
//                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
//                tokenStore.removeAccessToken(accessToken);
//
//                //OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(tokenValue);
//                OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
//                tokenStore.removeRefreshToken(refreshToken);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Invalid access token");
//        }

//        return ResponseEntity.ok().body("Access token invalidated successfully");
//    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDTO> authenticate(@Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult) {
        SuccessResponseDTO successResponseDTO = SuccessResponseDTO.builder()
                .data(userMapper.login(request, bindingResult))
                .code("200")
                .success(true)
                .build();
        return ResponseEntity.ok(successResponseDTO);
    }

    @GetMapping
    public String test() {
        return "test";
    }
    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(name = "username", defaultValue = "") String username) {
        try {
            var users = userService.searchByUserName(username.toLowerCase(Locale.ROOT));
            var usersDto = users.stream().map(u -> mapper.map(u, AuthUserResponse.class));
            return ResponseEntity.ok(SuccessResponseDTO.builder()
                    .data(usersDto)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(FailResponseDTO.builder()
                    .data(null)
                    .code("400")
                    .success(false)
                    .errors_message("Error : " + e.getMessage())
                    .build());
        }
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
