package com.cardinalis.userservice.service;

import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.projection.user.UserProjection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {
    public UserEntity save(RegisterDTO register);
    public List<UserEntity> searchByUserName(String username);
    public UserEntity fetchByUsername(String username);
    public List<String> getFollowingList(String username);
    public UserEntity updateUser(UUID id, UserEntityDTO requestDTO);
    Page<UserProjection> getFollowers(Long userId, Pageable pageable);

    Page<UserProjection> getFollowing(Long userId, Pageable pageable);

    Map<String, Object> processFollow(Long userId);
}
