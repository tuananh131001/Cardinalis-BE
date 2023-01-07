package com.cardinalis.userservice.dao.response.notification;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NotificationUserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String avatar;

    @JsonProperty("isFollower")
    private boolean isFollower;
}