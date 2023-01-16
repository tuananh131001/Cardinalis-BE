package org.cardinalis.tweetservice.Tweet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardinalis.tweetservice.DTO.TweetAuthorDTO;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetDTO {

    private Long id;
    private String email;
    private Long userid;
    private String username;
    private String avatar;
    private String fullname;

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
        this.createdAt = tweet.getCreatedAt();
        this.lastEdit = tweet.getLastEdit();
        this.content = tweet.getContent();
        this.totalFav = tweet.getFav().stream().count();
        this.totalComment = tweet.getComments().stream().count();
    }
    public TweetDTO(Tweet tweet, TweetAuthorDTO authorDTO) {
        this.id = tweet.getId();
        this.email = authorDTO.getEmail();
        this.username = authorDTO.getUsername();
        this.avatar = authorDTO.getAvatar();
        this.fullname = authorDTO.getFullName();
        this.userid = authorDTO.getId();
        this.createdAt = tweet.getCreatedAt();
        this.lastEdit = tweet.getLastEdit();
        this.content = tweet.getContent();
        this.totalFav = tweet.getFav().stream().count();
        this.totalComment = tweet.getComments().stream().count();
    }

    public TweetDTO(TweetAuthorDTO authorDTO) {
        this.email = authorDTO.getEmail();
        this.username = authorDTO.getUsername();
        this.avatar = authorDTO.getAvatar();
        this.fullname = authorDTO.getFullName();
        this.userid = authorDTO.getId();
    }

}
