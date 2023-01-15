package org.cardinalis.tweetservice.Kafka;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cardinalis.tweetservice.Comment.Comment;
import org.cardinalis.tweetservice.Comment.CommentService;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetService;
import org.cardinalis.tweetservice.ReplyComment.Reply;
import org.cardinalis.tweetservice.ReplyComment.ReplyService;
import org.cardinalis.tweetservice.Timeline.TimelineService;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetService;
import org.cardinalis.tweetservice.DTOUser.AuthUserResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.cardinalis.tweetservice.Util.Reusable.importUserInfo;


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
    @Autowired
    RestTemplate restTemplate;

    @KafkaListener(topics = "saveTweet", groupId = "group_id")
    public Tweet saveTweet(Tweet tweet) throws Exception  {
        String url = "http://localhost:3003/user/fetch/email="+tweet.getEmail();
//        String url = "http://cardinalis-be.live/user/fetch/email="+tweet.getEmail();
        System.out.println("hahaaaaaa");
        ResponseEntity<Map> restResponse = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> map = restResponse.getBody();
        Map<String, Object> userData = (Map) map.get("data");
        tweet.setUserid(Long.parseLong(userData.get("id").toString()));
        tweet.setUsername((String) userData.get("username"));
        tweet.setAvatar((String) userData.get("avatar"));
        tweet = tweetService.saveTweet(tweet);
//        System.out.println(tweet);
        timelineService.saveTweet(tweet);
        return tweet;
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
    public FavoriteTweet deleteFav(Long tweetId, String email) {
        return favoriteTweetService.deleteFavorite(tweetId, email);
    }

//    @KafkaListener(topics = "tweetRequireUserInfo", groupId = "group_id")
//    public void tweetRequireUserInfo(String email) {
//        kafkaProducer.send("userProcessUserInfo", email);
//    }
//
//    @KafkaListener(topics = "tweetRequireFollowingList", groupId = "group_id")
//    public void tweetRequireFollowingList(String email) {
//        kafkaProducer.send("userProcessFollowingList", email);
//    }
//
//    @KafkaListener(topics = "returnUserInfo", groupId = "group_id")
//    public TweetAuthorDTO returnUserInfo(TweetAuthorDTO user) {
//        return user;
//    }
}
