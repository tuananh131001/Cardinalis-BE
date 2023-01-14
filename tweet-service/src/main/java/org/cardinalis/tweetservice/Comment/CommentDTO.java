package org.cardinalis.tweetservice.Comment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardinalis.tweetservice.Tweet.Tweet;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    private String usermail;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastEdit;


    private String content;

    private long totalReply;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.usermail = comment.getUsermail();
        this.createdAt = comment.getCreatedAt();
        this.lastEdit = comment.getLastEdit();
        this.content = comment.getContent();
        this.totalReply = comment.getReplies().stream().count();
    }

}
