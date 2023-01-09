package org.cardinalis.tweetservice.Comment;

import java.util.Map;

public interface CommentService {

    Comment saveComment(Comment comment);

    Comment editComment(Comment comment);
    Comment deleteCommentById(Long id);

    Comment deleteComment(Comment comment);

    Comment getCommentById(Long id);

    Map<String, Object> getCommentsOfTweet(Long tweetId, String sort, int pageNo, int pageSize);


}
