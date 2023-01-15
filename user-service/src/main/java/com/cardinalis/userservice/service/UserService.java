package com.cardinalis.userservice.service;

import com.cardinalis.userservice.dao.RegisterDTO;
import com.cardinalis.userservice.dao.response.AuthUserResponse;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.projection.user.FollowerUserProjection;
import com.cardinalis.userservice.repository.projection.user.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> save(RegisterDTO register);
    public List<UserEntity> searchByUserName(String username);
    public UserEntity fetchByUsername(String username);
//    public List<String> getFollowingList(String username);
    public UserEntity updateUser( AuthUserResponse requestDTO);
    Page<UserProjection> getFollowers(Long userId, Pageable pageable);
    Page<FollowerUserProjection> getFollowerRequests(Pageable pageable);
    public String acceptFollowRequest(Long userId);
    Boolean processUserList(UserEntity currentUser, List<UserEntity> userLists);
    Boolean processSubscribeToNotifications(Long userId);
    Page<UserProjection> getFollowing(Long userId, Pageable pageable);

    Map<String, Object> processFollow(Long userId);
    String declineFollowRequest(Long userId);
    Map<String, Object> login(String email, String password);
}
