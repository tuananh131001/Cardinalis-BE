package org.cardinalis.tweetservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
//@Data
@Getter
@Setter
@Entity
@Table(name="Tweet")
@NoArgsConstructor
@AllArgsConstructor
public class Tweet implements Comparable<Tweet>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(nullable = false, length = 36)
    private UUID id;

    @Column(nullable = false, length = 36)
    private String username;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "content")
    private String content = "";

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<FavoriteTweet> fav = new ArrayList<>();


    @Override
    public int compareTo(Tweet tweet) {
        if (this.createdAt == null || tweet.createdAt == null) {
            return 0;
        }
        return this.createdAt.compareTo(tweet.createdAt);
    }
}
