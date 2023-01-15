package com.cardinalis.userservice.repository.projection.user;

public interface FollowerUserProjection {
    Long getId();
    String getFullName();
    String getEmail();
    String getUsername();
    String getAbout();
    String getAvatar();
}

