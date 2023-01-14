package com.cardinalis.userservice.repository.projection.user;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface AuthUserProjection {
    Long getId();
    String getEmail();
    String getFullName();
    String getUsername();
    String getLocation();

    String getAbout();
    String getWebsite();
    String getCountryCode();

    Long getPhone();
    String getCountry();
    String getGender();
    String getLanguage();
    String getBirthday();
    LocalDateTime getRegistrationDate();
    Long getTweetCount();
    Long getMediaTweetCount();
    Long getLikeCount();
    Long getNotificationsCount();
    boolean isActive();
    boolean isProfileCustomized();
    boolean isProfileStarted();
    boolean isMutedDirectMessages();
    boolean isPrivateProfile();

    String getAvatar();


    @Value("#{target.followers.size()}")
    Integer getFollowersSize();

    @Value("#{target.following.size()}")
    Integer getFollowingSize();

    @Value("#{target.followerRequests.size()}")
    Integer getFollowerRequestsSize();

    @Value("#{target.unreadMessages.size()}")
    Integer getUnreadMessagesSize();
}