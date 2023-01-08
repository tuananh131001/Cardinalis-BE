package com.cardinalis.userservice.config;

import com.cardinalis.userservice.model.Role;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.RoleRepository;
import com.cardinalis.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartUp {


    final private StartupProperties startupProperties;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, RoleRepository roleRepository) {
        return (args) -> {
            List<UserEntity> users = userRepository.findAll();

            if (ObjectUtils.isEmpty(users)) {
                roleRepository.save(saveRole(1L, "ADMIN"));
                roleRepository.save(saveRole(2L, "USER"));
                userRepository.save(saveUser());
                userRepository.save(saveUser2());
            }
        };
    }

    public Role saveRole(Long id, String name) {
        return new Role().builder()
                .id(id)
                .name(name)
                .build();
    }

    public UserEntity saveUser() {
        Role role = new Role().builder()
                .id(1L)
                .name("ADMIN")
                .build();

        return UserEntity.builder()
                .username(startupProperties.getUsername())
                .email(startupProperties.getEmail())
                .password(passwordEncoder.encode(startupProperties.getPassword()))
//                .password("9999")
                .fullName("Thanh NN")
                .location("HCM")
                .bio("Teacher at RMIT")
                .website("https://www.rmit.edu.vn")
                .countryCode("84")
                .phone(123456789L)
                .country("Vietnam")
                .gender("Male")
                .dateOfBirth(LocalDateTime.now())
                .avatar("https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg")
                .notificationsCount(0L)
                .createdAt(LocalDateTime.now())
                .lastLoginTime(LocalDateTime.now())
                .isHotUser(true)
                .roles(Arrays.asList(role)).build();
    }
    // add 5 more people
    public UserEntity saveUser2() {
        Role role = new Role().builder()
                .id(2L)
                .name("USER")
                .build();

        return UserEntity.builder()
                .username("Thanh NN 2")
                .email("thanhnn2@gmail.com")
                .password(passwordEncoder.encode(startupProperties.getPassword()))
//                .password("9999")
                .fullName("Thanh PA")
                .location("HCM")
                .bio("Teacher 2 at RMIT")
                .website("https://www.rmit.edu.vn")
                .countryCode("84")
                .phone(123456789L)
                .country("Vietnam")
                .gender("Male")
                .dateOfBirth(LocalDateTime.now())
                .avatar("https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg")
                .notificationsCount(0L)
                .createdAt(LocalDateTime.now())
                .lastLoginTime(LocalDateTime.now())
                .isHotUser(true)
                .roles(Arrays.asList(role)).build();
    }

}