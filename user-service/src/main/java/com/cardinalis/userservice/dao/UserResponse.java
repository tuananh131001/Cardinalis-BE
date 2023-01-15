package com.cardinalis.userservice.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String fullName;
    private String username;
    private String bio;
    private String avatar;

    @JsonProperty("isFollower")
    private boolean isFollower;
}