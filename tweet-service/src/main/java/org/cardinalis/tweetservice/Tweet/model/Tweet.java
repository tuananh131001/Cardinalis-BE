package org.cardinalis.tweetservice.Tweet.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@Table(name="Tweet")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Tweet implements Comparable<Tweet>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private String content = "";

//    @Column
//    private long totalFav = 0;
//
//    @Column
//    private long totalComment = 0;

//    @JsonBackReference
//    @OneToMany(mappedBy = "tweet")
//    private List<FavoriteTweet> fav;
//
//    @JsonBackReference
//    @OneToMany(mappedBy = "tweet")
//    private List<Comment> comments;

    @Override
    public int compareTo(Tweet tweet) {
        if (this.createdAt == null || tweet.createdAt == null) {
            return 0;
        }
        return this.createdAt.compareTo(tweet.createdAt);
    }

}
