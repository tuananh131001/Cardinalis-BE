package com.cardinalis.userservice.service;
import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.dao.UserShortInfoDTO;
import com.cardinalis.userservice.model.Relationship;
import com.cardinalis.userservice.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    Relationship follow(Relationship relationship);

    List<UserEntityDTO> getFollowing(UUID followerId);

    List<UserEntityDTO> getFollowers(UUID followedId);

    void unfollow(UUID followedId, UUID followerId);

}