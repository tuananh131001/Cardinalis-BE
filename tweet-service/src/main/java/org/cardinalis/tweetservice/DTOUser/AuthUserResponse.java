package org.cardinalis.tweetservice.DTOUser;

import lombok.*;

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
    private String website;
    private String countryCode;
    private Long phone;
    private String country;
    private String gender;
    private String dateOfBirth;
    private LocalDateTime createdAt;
    private Long notificationsCount;
    private String avatar;
}
