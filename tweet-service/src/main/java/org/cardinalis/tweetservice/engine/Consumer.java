package org.cardinalis.tweetservice.engine;


import org.cardinalis.tweetservice.Comment.Comment;
import org.cardinalis.tweetservice.Comment.CommentService;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetService;
import org.cardinalis.tweetservice.ReplyComment.Reply;
import org.cardinalis.tweetservice.ReplyComment.ReplyService;
import org.cardinalis.tweetservice.Timeline.TimelineService;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class Consumer {
    @Autowired
    TweetService tweetService;
    @Autowired
    FavoriteTweetService favoriteTweetService;
    @Autowired
    TimelineService timelineService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;

    @KafkaListener(topics = "saveTweet", groupId = "group_id")
    public void saveTweet(Tweet tweet) throws Exception  {
        tweet = tweetService.saveTweet(tweet);
        timelineService.saveTweet(tweet);
    }

    @KafkaListener(topics = "deleteTweet", groupId = "group_id")
    public Tweet deleteTweet(Long id) throws Exception  {
        timelineService.deleteTweet(id);
        return tweetService.deleteTweet(id);
    }

    @KafkaListener(topics = "saveComment", groupId = "group_id")
    public Comment saveComment(Comment comment) {
        return commentService.saveComment(comment);
    }

    @KafkaListener(topics = "saveReply", groupId = "group_id")
    public Reply saveReply(Reply reply) {
        return replyService.saveReply(reply);
    }

    @KafkaListener(topics = "saveFav", groupId = "group_id")
    public FavoriteTweet saveFav(FavoriteTweet favoriteTweet) {
        return favoriteTweetService.saveFavorite(favoriteTweet);
    }

    @KafkaListener(topics = "deleteFav", groupId = "group_id")
    public FavoriteTweet deleteFav(Long tweetId, String usermail) {
        return favoriteTweetService.deleteFavorite(tweetId, usermail);
    }
}
