package org.cardinalis.tweetservice.Kafka;


import org.cardinalis.tweetservice.Comment.Comment;
import org.cardinalis.tweetservice.Comment.CommentService;
import org.cardinalis.tweetservice.DTO.TweetAuthorDTO;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetService;
import org.cardinalis.tweetservice.ReplyComment.Reply;
import org.cardinalis.tweetservice.ReplyComment.ReplyService;
import org.cardinalis.tweetservice.Timeline.TimelineService;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.DTO.TweetDTOKafka;
import org.cardinalis.tweetservice.Tweet.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class KafkaConsumer {
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

//    @Autowired
//    KafkaProducer kafkaProducer;

    @KafkaListener(topics = "saveTweet", groupId = "group_id")
    public Tweet saveTweet(Tweet tweet) throws Exception  {
//        String url = env.getProperty("constant.user_local_url") + "/user/serverExchangeData/"+tweet.getEmail();
////        String url = "http://cardinalis-be.live/user/fetch/email="+tweet.getEmail();
//        ResponseEntity<Map> restResponse = restTemplate.getForEntity(url, Map.class);
//        Map<String, TweetAuthorDTO> authorDTOMap = restResponse.getBody();
        tweet = tweetService.saveTweet(tweet);
        timelineService.saveTweet(tweet);
        return tweet;
    }

    @KafkaListener(topics = "deleteTweet", groupId = "group_id")
    public Tweet deleteTweet(String idString) throws Exception  {
        Long id = Long.parseLong(idString);
        tweetService.deleteTweet(id);
        timelineService.deleteTweet(id);
        return tweetService.deleteTweet(id);
    }
//
    @KafkaListener(topics = "saveComment", groupId = "group_id")
    public Comment saveComment(Comment comment) {
        return commentService.saveComment(comment);
    }

    @KafkaListener(topics = "saveReply", groupId = "group_id")
    public Reply saveReply(Reply reply) {
        return replyService.saveReply(reply);
    }
//
    @KafkaListener(topics = "saveFav", groupId = "group_id")
    public FavoriteTweet saveFav(FavoriteTweet favoriteTweet) throws Exception {
        return favoriteTweetService.saveFavorite(favoriteTweet);
    }
//
    @KafkaListener(topics = "deleteFav", groupId = "group_id")
    public FavoriteTweet deleteFav(Long tweetId, String email) throws Exception {
        return favoriteTweetService.deleteFavorite(tweetId, email);
    }
}
