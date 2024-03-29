package com.cardinalis.userservice.model;

import com.cardinalis.userservice.enums.AuthenticationProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "avatar")
    private String avatar;  // store the avatar image as a string

    @Column(name = "full_name")
    private String fullName;  // store the avatar image as a string
    @Column(name = "username", length = 100, unique = true)
    private String username;

    @Column(name = "email", length = 200, unique = true)
    private String email;
    @Column(name = "bio")
    private String bio;

    @Column(name = "website")
    private String website;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone")
    private Long phone;
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "country")
    private String country;

    @Column(name = "gender")
    private String gender;
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
    @Column(name = "activation_code")
    private String activationCode;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;

    private String providerId;

    @ManyToMany(fetch = FetchType.EAGER)
    //@Builder.Default
    private List<Role> roles = new ArrayList();


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "follower_following",
            joinColumns = { @JoinColumn(name = "follower_id") },
            inverseJoinColumns = { @JoinColumn(name = "following_id") })
    @JsonIgnoreProperties("followers")
    private List<UserEntity> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("following")
    private List<UserEntity> followers = new ArrayList<>();


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