package com.cardinalis.userservice.dao.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuthUserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String username;
    private String location;
    private String bio;
    private String banner;
    private String website;
    private String countryCode;
    private Long phone;
    private String country;
    private String gender;
    private LocalDateTime dateOfBirth;
    private LocalDateTime createdAt;
    private Long notificationsCount;
    private String avatar;
}
