package com.cardinalis.userservice.repository.projection.user;

import lombok.Value;

public interface UserProjection {
    Long getId();
    String getFullName();
    String getUsername();
    String getBio();
    String getAvatar();

    @Value("#{@userServiceImpl.isUserBlockedByMyProfile(target.id)}")
    boolean getIsUserBlocked();

    @Value("#{@userServiceImpl.isMyProfileWaitingForApprove(target.id)}")
    boolean getIsWaitingForApprove();

    @Value("#{@userServiceImpl.isUserFollowByOtherUser(target.id)}")
    boolean getIsFollower();
}