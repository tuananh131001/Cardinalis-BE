package com.cardinalis.userservice.service;

import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.projection.user.FollowerUserProjection;
import com.cardinalis.userservice.repository.projection.user.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {
    public UserEntity save(RegisterDTO register);
    public List<UserEntity> searchByUserName(String username);
    public UserEntity fetchByUsername(String username);
//    public List<String> getFollowingList(String username);
    public UserEntity updateUser(Long id, UserEntityDTO requestDTO);
    Page<UserProjection> getFollowers(Long userId, Pageable pageable);
    Page<FollowerUserProjection> getFollowerRequests(Pageable pageable);
    public String acceptFollowRequest(Long userId);
    Boolean processUserList(UserEntity currentUser, List<UserEntity> userLists);
    Boolean processSubscribeToNotifications(Long userId);
    Page<UserProjection> getFollowing(Long userId, Pageable pageable);

    Map<String, Object> processFollow(Long userId);
    String declineFollowRequest(Long userId);
}
