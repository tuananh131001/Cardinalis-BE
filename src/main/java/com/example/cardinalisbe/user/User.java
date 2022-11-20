package com.example.cardinalisbe.user;

import com.example.cardinalisbe.follower.Follower;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Follower> follows;

}
