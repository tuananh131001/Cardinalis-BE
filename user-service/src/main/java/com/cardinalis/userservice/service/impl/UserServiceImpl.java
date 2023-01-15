package com.cardinalis.userservice.service.impl;
import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.dao.response.AuthUserResponse;
import com.cardinalis.userservice.enums.NotificationType;
import com.cardinalis.userservice.exception.ApiRequestException;
import com.cardinalis.userservice.exception.NoContentFoundException;
import com.cardinalis.userservice.model.Notification;
import com.cardinalis.userservice.model.Role;
import com.cardinalis.userservice.repository.NotificationRepository;
import com.cardinalis.userservice.repository.RelationshipRepository;
import com.cardinalis.userservice.repository.UserRepository;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.projection.user.AuthUserProjection;
import com.cardinalis.userservice.repository.projection.user.FollowerUserProjection;
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
    private final NotificationRepository notificationRepository;
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
        user.setNotificationsCount(0L);
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


    @Transactional
    @Override
    public UserEntity updateUser(Long id, AuthUserResponse requestDTO) {
        log.info("Update user {}", id);
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
        userFound.setNotificationsCount(requestDTO.getNotificationsCount());
        userFound.setAvatar(requestDTO.getAvatar());
        return userRepository.save(userFound);
    }
    @Override
    @Transactional
    public Map<String, Object> processFollow(Long userId) {
        UserEntity user = authenticationService.getAuthenticatedUser();
        UserEntity currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        List<UserEntity> followers = user.getFollowers();
        Optional<UserEntity> follower = followers.stream()
                .filter(f -> f.getId().equals(currentUser.getId()))
                .findFirst();
        boolean isFollower;
        if(userId == user.getId()){
            throw new ApiRequestException("You can't follow yourself", HttpStatus.BAD_REQUEST);
        }
        if (follower.isPresent()) {
            followers.remove(follower.get());
            List<UserEntity> subscribers = currentUser.getSubscribers();
            Optional<UserEntity> subscriber = subscribers.stream()
                    .filter(s -> s.getId().equals(user.getId()))
                    .findFirst();
            subscriber.ifPresent(subscribers::remove);
            isFollower = false;
        } else {
            followers.add(currentUser);
            isFollower = true;
        }

        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.FOLLOW);
        notification.setUser(user);
        notification.setUserToFollow(currentUser);
        notification.setNotifiedUser(currentUser);

        if (!currentUser.getId().equals(user.getId())) {
            Optional<Notification> userNotification = currentUser.getNotifications().stream()
                    .filter(n -> n.getNotificationType().equals(NotificationType.FOLLOW)
                            && n.getUser().getId().equals(user.getId()))
                    .findFirst();

            if (userNotification.isEmpty()) {
                Notification newNotification = notificationRepository.save(notification);
                currentUser.setNotificationsCount(currentUser.getNotificationsCount() + 1);
                List<Notification> notifications = currentUser.getNotifications();
                notifications.add(newNotification);
                return Map.of("notification", newNotification, "isFollower", isFollower);
            }
        }
        return Map.of("notification", notification, "isFollower", isFollower);
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
    @Override
    public Page<FollowerUserProjection> getFollowerRequests(Pageable pageable) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return userRepository.getFollowerRequests(authUserId, pageable);
    }
    @Override
    @Transactional
    public String acceptFollowRequest(Long userId) {
        UserEntity user = authenticationService.getAuthenticatedUser();
        UserEntity currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        user.getFollowerRequests().remove(currentUser);
        user.getFollowers().add(currentUser);
        return "User (id:" + userId + ") accepted.";
    }
    @Override
    @Transactional
    public String declineFollowRequest(Long userId) {
        UserEntity user = authenticationService.getAuthenticatedUser();
        UserEntity currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        user.getFollowerRequests().remove(currentUser);
        return "User (id:" + userId + ") declined.";
    }
    @Override
    @Transactional
    public Boolean processSubscribeToNotifications(Long userId) {
        UserEntity user = authenticationService.getAuthenticatedUser();
        UserEntity currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        return processUserList(user, currentUser.getSubscribers());
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