package org.cardinalis.tweetservice.Tweet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetDTO {

    private Long id;

    private String email;

    @Column
    private Long userid;

    @Column
    private String username;

    @Column
    private String avatar;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastEdit;


    private String content;

    private long totalFav;

    private long totalComment;

    public TweetDTO(Tweet tweet) {
        this.id = tweet.getId();
        this.email = tweet.getEmail();
        this.username = tweet.getUsername();
        this.avatar = tweet.getAvatar();
        this.userid = tweet.getId();
        this.createdAt = tweet.getCreatedAt();
        this.lastEdit = tweet.getLastEdit();
        this.content = tweet.getContent();
        this.totalFav = tweet.getFav().stream().count();
        this.totalComment = tweet.getComments().stream().count();
    }

}
