package org.cardinalis.tweetservice.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FavoriteTweetId.class)
@Table(name = "FavoriteTweet")
public class FavoriteTweet {
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "tweetId")
    private UUID tweetId;

    @Id
    @Type(type = "org.hibernate.type.StringType")
    @Column(name = "username", length = 36)
    private String username;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
