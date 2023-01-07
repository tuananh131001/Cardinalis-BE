package com.cardinalis.userservice.security.oauth;

import com.cardinalis.userservice.model.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Data
public class CustomOAuth2User implements OAuth2User {
    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomOAuth2User(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomOAuth2User create(UserEntity user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomOAuth2User(
                // to uuid
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static CustomOAuth2User create(UserEntity user, Map<String, Object> attributes) {
        CustomOAuth2User userPrincipal = CustomOAuth2User.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
