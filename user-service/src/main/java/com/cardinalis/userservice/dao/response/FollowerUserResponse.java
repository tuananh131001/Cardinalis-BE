package com.cardinalis.userservice.dao.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowerUserResponse {
    private Long id;
    private String fullName;
    private String username;
    private String about;
    private String avatar;
}
