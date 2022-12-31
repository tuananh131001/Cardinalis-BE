package com.cardinalis.userservice.service;
import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.exception.NoContentFoundException;
import com.cardinalis.userservice.model.Relationship;
import com.cardinalis.userservice.model.Role;
import com.cardinalis.userservice.repository.RelationshipRepository;
import com.cardinalis.userservice.repository.UserRepository;
import com.cardinalis.userservice.model.UserEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RelationshipRepository relationshipRepository;

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

    public UserEntity updateUser(UUID id, UserEntityDTO requestDTO) {
    }
}