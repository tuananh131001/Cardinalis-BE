package com.cardinalis.userservice.service;
import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.exception.NoContentFoundException;
import com.cardinalis.userservice.model.Role;
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

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Transactional
    public UserEntity save(RegisterDTO register) {
        Optional<UserEntity> userExists = Optional.of(userRepository.findByUsername(register.getUsername()))
                .or(() -> Optional.of(userRepository.findByEmail(register.getEmail())))
                .orElse(null);

        if (userExists.isPresent())
            throw new IllegalArgumentException("User " + register.getUsername() + " " + register.getEmail() + " already exists");

        var user = mapper.map(register, UserEntity.class);
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
}