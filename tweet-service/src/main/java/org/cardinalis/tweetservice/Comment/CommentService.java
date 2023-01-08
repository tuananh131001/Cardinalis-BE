package org.cardinalis.tweetservice.Comment;

import org.springframework.data.domain.Sort;

import java.util.Map;

public interface CommentService {

    Comment saveComment(Comment commentTweet);

    Comment editComment(Comment commentTweet);

    Comment deleteCommentByTweet_IdAndUsername(Long tweetId, String username);
    Comment deleteCommentById(Long id);

    Comment getCommentByTweet_IdAndUsername(Long tweetId, String username);

    Comment getCommentById(Long id);

    Map<String, Object> getCommentsByTweet(Long tweetId, String sort, int pageNo, int pageSize);

    Map<String, Object> getAll(String sort, int pageNo, int pageSize);

}
