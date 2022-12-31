package com.cardinalis.userservice.service;
import com.cardinalis.userservice.model.Relationship;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    Relationship follow(Relationship relationship);

    List<Relationship> getFollowing(UUID followerId);

    List<Relationship> getFollowers(UUID followedId);

    void unfollow(UUID followedId, UUID followerId);

}