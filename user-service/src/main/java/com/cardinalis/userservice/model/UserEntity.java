package com.cardinalis.userservice.model;

import com.cardinalis.userservice.enums.AuthenticationProvider;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(nullable = false, length = 36)
    private Long id;

    @Column(name = "avatar")
    private String avatar;  // store the avatar image as a string

    @Column(name = "fullName")
    private String fullName;  // store the avatar image as a string
    @Column(name = "username", length = 100, unique = true)
    private String username;

    @Column(name = "email", length = 200, unique = true)
    private String email;
    @Column(name = "bio")
    private String bio;
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;
    @Column(name = "location")
    private String location;
    @Column(name = "banner")
    private String banner;

    @Column(name = "password")
    private String password;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "is_hot_user")
    private Boolean isHotUser;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;

    private String providerId;

    @ManyToMany(fetch = FetchType.EAGER)
    //@Builder.Default
    private List<Role> roles = new ArrayList();

    @ManyToMany
    @JoinTable(name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private List<UserEntity> followers;

    @ManyToMany
    @JoinTable(name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> following;
    @ManyToMany
    @JoinTable(name = "user_follower_requests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<UserEntity> followerRequests;

    @ManyToMany
    @JoinTable(name = "subscribers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private List<UserEntity> subscribers;

    @OneToMany
    private List<Notification> notifications;

    @Column(name = "notifications_count", columnDefinition = "int8 default 0")
    private Long notificationsCount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}