package com.example.cardinalisbe.follower;

import com.example.cardinalisbe.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Follower {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "followee")
    private User followee;

    @ManyToOne
    @JoinColumn(name = "follower")
    private User follower;
}
