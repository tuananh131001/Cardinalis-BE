package com.cardinalis.userservice.service;

import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserEntity save(RegisterDTO register);
    public List<UserEntity> searchByUserName(String username);
    public UserEntity fetchByUsername(String username);
    public List<String> getFollowingList(String username);
    public UserEntity updateUser(UUID id, UserEntityDTO requestDTO);
}
