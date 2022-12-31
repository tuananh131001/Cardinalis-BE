package com.cardinalis.userservice.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityDTO {
    private UUID id;

    private String avatar;

    private String username;

    private String email;
    private String bio;
    private LocalDateTime dateOfBirth;
    private String location;
    private String thumbnail = "";
    @JsonIgnore
    private String password;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("last_login_time")
    private LocalDateTime lastLoginTime;

    @JsonProperty("is_hot_user")
    private Boolean isHotUser;


}