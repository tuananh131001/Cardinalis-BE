package com.cardinalis.userservice.service.impl;
import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.dao.response.AuthUserResponse;
import com.cardinalis.userservice.exception.ApiRequestException;
import com.cardinalis.userservice.exception.NoContentFoundException;
import com.cardinalis.userservice.model.Role;
import com.cardinalis.userservice.repository.RelationshipRepository;
import com.cardinalis.userservice.repository.UserRepository;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.projection.user.UserProjection;
import com.cardinalis.userservice.security.JwtProvider;
import com.cardinalis.userservice.service.AuthenticationService;
import com.cardinalis.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final RelationshipRepository relationshipRepository;
    private final AuthenticationService authenticationService;
    private final JwtProvider jwtProvider;
    @Autowired
    private final ModelMapper mapper;

    @Transactional
    @Override
    public  Map<String, Object> save(RegisterDTO register) {
        // Check if the email already exists in the system
        Optional<UserEntity> emailExists = userRepository.findByEmail(register.getEmail());
        if (emailExists.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Check if the username already exists in the system
        Optional<UserEntity> usernameExists = userRepository.findByUsername(register.getUsername());
        if (usernameExists.isPresent()) {
            throw new IllegalArgumentException("User name  already exists");
        }

        // Create the new user if the email and username do not already exist in the system
        var user = mapper.map(register, UserEntity.class);
        String token = jwtProvider.createToken(register.getEmail(), "USER");
        user.setAvatar("https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg");
        user.setIsHotUser(true);
        // set 0 as Long type
        user.setBanner("https://destination-review.com/wp-content/uploads/2022/12/anton-shuvalov-vGcRek7WS5s-unsplash-1-1200x600.jpg");
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());
        user.setRoles(List.of(Role.builder().id(2L).name("USER").build()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Map<String, Object> response = new HashMap<>();
        UserEntity userReturn = userRepository.save(user);
        response.put("user", userReturn);
        response.put("token", token);
        return response;
    }
    public List<UserEntity> searchByUserName(String username) {
        var users = userRepository.findByUsernameContaining(username);
        if (users.isEmpty()) {
            throw new NoContentFoundException("User not found");
        }
        return users;
    }


    public UserEntity fetchByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoContentFoundException("User not found"));
    }

    public UserEntity fetchByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoContentFoundException("User not found"));
    }


    @Transactional
    @Override
    public UserEntity updateUser(AuthUserResponse requestDTO) {
        UserEntity user = authenticationService.getAuthenticatedUser();
        UserEntity userFound = userRepository.findById(user.getId())
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        userFound.setEmail(requestDTO.getEmail());
        userFound.setFullName(requestDTO.getFullName());
        userFound.setUsername(requestDTO.getUsername());
        userFound.setLocation(requestDTO.getLocation());
        userFound.setBio(requestDTO.getBio());
        userFound.setWebsite(requestDTO.getWebsite());
        userFound.setCountryCode(requestDTO.getCountryCode());
        userFound.setPhone(requestDTO.getPhone());
        userFound.setCountry(requestDTO.getCountry());
        userFound.setGender(requestDTO.getGender());
        userFound.setDateOfBirth(requestDTO.getDateOfBirth());
        userFound.setAvatar(requestDTO.getAvatar());
        return userRepository.save(userFound);
    }
    @Override
    @Transactional
    public Map<String, Object> processFollow(Long userId) {
        UserEntity currentUser = authenticationService.getAuthenticatedUser();
        UserEntity userToFollow = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        Map<String, Object> response = new HashMap<>();
        //check user follow myself
        if (currentUser.getId().equals(userToFollow.getId())) {
            throw new ApiRequestException("You can't follow yourself", HttpStatus.BAD_REQUEST);
        }
        if (currentUser.getFollowing().contains(userToFollow)) { // unfollow
            //unfollow
            currentUser.getFollowing().remove(userToFollow);
            userToFollow.getFollowers().remove(currentUser);
            userRepository.save(currentUser);
            userRepository.save(userToFollow);
            response.put("message", "Unfollow Successful!");
        } else {
            //follow
            currentUser.getFollowing().add(userToFollow);
            userToFollow.getFollowers().add(currentUser);
            userRepository.save(currentUser);
            userRepository.save(userToFollow);
            response.put("message", "Follow Successful!");
        }
        response.put("user", userToFollow);
        return response;
    }
    @Override
    public Page<UserProjection> getFollowers(Long userId, Pageable pageable) {
        checkIsUserExist(userId);
        return userRepository.getFollowersById(userId, pageable);
    }

    @Override
    public Page<UserProjection> getFollowing(Long userId, Pageable pageable) {
        checkIsUserExist(userId);
        return userRepository.getFollowingById(userId, pageable);
    }
    private void checkIsUserExist(Long userId) {
        boolean userExist = userRepository.isUserExist(userId);

        if (!userExist) {
            throw new ApiRequestException("User (id:" + userId + ") not found", HttpStatus.NOT_FOUND);
        }

    }
    public boolean isUserFollowByOtherUser(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return userRepository.isUserFollowByOtherUser(authUserId, userId);
    }


    public Boolean processUserList(UserEntity currentUser, List<UserEntity> userLists) {
        Optional<UserEntity> userFromList = userLists.stream()
                .filter(user -> user.getId().equals(currentUser.getId()))
                .findFirst();

        if (userFromList.isPresent()) {
            userLists.remove(userFromList.get());
            return false;
        } else {
            userLists.add(currentUser);
            return true;
        }
    }
    @Override
    public Map<String, Object> login(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            UserEntity user = userRepository.findAuthUserByEmail(email)
                    .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
            String token = jwtProvider.createToken(email, "USER");
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);
            return response;
        } catch (AuthenticationException e) {
            throw new ApiRequestException("Incorrect password or email.", HttpStatus.FORBIDDEN);
        }
    }
}