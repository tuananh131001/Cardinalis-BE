package com.cardinalis.userservice.dao;

import com.cardinalis.userservice.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetAuthorDTO implements Serializable {
    private Long id;
    private String fullName;
    private String email;
    private String username;
    private String avatar;

    public TweetAuthorDTO(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
    }
}