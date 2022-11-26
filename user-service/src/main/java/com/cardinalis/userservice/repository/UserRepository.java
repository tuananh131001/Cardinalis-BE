package com.cardinalis.userservice.repository;

import com.cardinalis.userservice.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
