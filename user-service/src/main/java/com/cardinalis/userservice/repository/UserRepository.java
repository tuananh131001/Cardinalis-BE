package com.cardinalis.userservice.repository;

import com.cardinalis.userservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
