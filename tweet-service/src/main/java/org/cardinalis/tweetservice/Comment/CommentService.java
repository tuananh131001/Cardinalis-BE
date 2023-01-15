package org.cardinalis.tweetservice.Comment;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Map;

@EnableKafka
public interface CommentService {

    @KafkaListener(topics = "saveComment", groupId = "group_id")
    Comment saveComment(Comment comment);

    Comment editComment(Comment comment);
    Comment deleteCommentById(Long id);

    Comment deleteComment(Comment comment);

    Comment getCommentById(Long id);

    Map<String, Object> getCommentsOfTweet(Long tweetId, String sort, int pageNo, int pageSize);


}
