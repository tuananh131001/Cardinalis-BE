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
    private String email = "thanhnn2@gmail.com";
    private Long userid = 2L;
    private String username = "Thanh NN 2";
    private String avatar = "https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg";
    private String fullname = "Thanh PA";

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastEdit;
    private String content = "";

    private long totalFav = 0;

    private long totalComment = 0;

    public TweetDTO(Tweet tweet) {
        this.id = tweet.getId();
        this.email = tweet.getEmail();
        this.createdAt = tweet.getCreatedAt() == null ? LocalDateTime.now() : tweet.getCreatedAt();
        this.lastEdit = tweet.getLastEdit();
        this.content = tweet.getContent() == null ? "" : tweet.getContent();
        if(tweet.getFav() != null) { this.totalFav = tweet.getFav().stream().count();}
        else this.totalFav = 0;
        if(tweet.getComments() != null) { this.totalFav = tweet.getComments().stream().count();}
        else this.totalComment = 0;
    }
    public TweetDTO(Tweet tweet, TweetAuthorDTO authorDTO) {
        this.id = tweet.getId();
        this.email = authorDTO.getEmail() == null ? "thanhnn2@gmail.com"  : authorDTO.getEmail();
        this.username = authorDTO.getUsername() == null ? "Thanh NN 2"  : authorDTO.getUsername();
        this.avatar = authorDTO.getAvatar() == null ? "https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg" : authorDTO.getAvatar();
        this.fullname = authorDTO.getFullName() == null ? "Thanh PA" :  authorDTO.getFullName();
        this.userid = authorDTO.getId();
        this.createdAt = tweet.getCreatedAt() == null ? LocalDateTime.now() : tweet.getCreatedAt();
        this.lastEdit = tweet.getLastEdit();
        this.content = tweet.getContent() == null ? "" : tweet.getContent();
        if(tweet.getFav() != null) { this.totalFav = tweet.getFav().stream().count();}
        else this.totalFav = 0;
        if(tweet.getComments() != null) { this.totalFav = tweet.getComments().stream().count();}
        else this.totalComment = 0;
    }

    public TweetDTO(TweetAuthorDTO authorDTO) {
        this.username = authorDTO.getUsername() == null ? "Thanh NN 2"  : authorDTO.getUsername();
        this.avatar = authorDTO.getAvatar() == null ? "https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg" : authorDTO.getAvatar();
        this.fullname = authorDTO.getFullName() == null ? "Thanh PA" :  authorDTO.getFullName();
        this.userid = authorDTO.getId();
    }

}
