package com.cardinalis.userservice.service.impl;
import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.enums.NotificationType;
import com.cardinalis.userservice.exception.ApiRequestException;
import com.cardinalis.userservice.exception.NoContentFoundException;
import com.cardinalis.userservice.model.Notification;
import com.cardinalis.userservice.model.Relationship;
import com.cardinalis.userservice.model.Role;
import com.cardinalis.userservice.repository.NotificationRepository;
import com.cardinalis.userservice.repository.RelationshipRepository;
import com.cardinalis.userservice.repository.UserRepository;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.service.AuthenticationService;
import com.cardinalis.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RelationshipRepository relationshipRepository;
    private final AuthenticationService authenticationService;
    private final NotificationRepository notificationRepository;
    @Autowired
    private final ModelMapper mapper;
    @Transactional
    public UserEntity save(RegisterDTO register) {
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
        user.setAvatar("https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg");
        user.setIsHotUser(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());
        user.setRoles(List.of(Role.builder().id(2L).name("USER").build()));
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);
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

    public List<String> getFollowingList(String username) {
        UserEntity user = fetchByUsername(username);
        List<Relationship> relationships = user.getFollows();
        if (relationships == null) return null;

        List<String> result = null;
        for (Relationship r: relationships) {
            UserEntity u = userRepository.findById(r.getFollowedId())
                    .orElseThrow(()  -> new NoContentFoundException("User not found"));
            result.add(u.getUsername());
        }
        return result;
    }

    @Transactional
    public UserEntity updateUser(UUID id, UserEntityDTO requestDTO) {
        log.info("Update user {}", id);
        var userFound = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User " + id + " not found"));
        userFound.setEmail(requestDTO.getEmail());
        userFound.setIsHotUser(requestDTO.getIsHotUser());
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
}