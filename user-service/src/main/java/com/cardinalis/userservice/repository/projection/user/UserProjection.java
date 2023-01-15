package com.cardinalis.userservice.repository.projection.user;

import org.springframework.beans.factory.annotation.Value;

public interface UserProjection {
    Long getId();
    String getFullName();
    String getUsername();
    String getEmail();
    String getBio();
    String getAvatar();

    @Value("#{@userServiceImpl.isUserFollowByOtherUser(target.id)}")
    boolean getIsFollower();

    @Value("#{@userServiceImpl.isFollowingOneWay(target.id)}")
    boolean getIsFollowing();
}