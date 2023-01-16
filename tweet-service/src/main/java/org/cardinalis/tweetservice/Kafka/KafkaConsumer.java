package org.cardinalis.tweetservice.Kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cardinalis.tweetservice.Comment.CommentService;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetService;
import org.cardinalis.tweetservice.ReplyComment.ReplyService;
import org.cardinalis.tweetservice.Timeline.TimelineService;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetDTOKafka;
import org.cardinalis.tweetservice.Tweet.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    RestTemplate restTemplate;

    @KafkaListener(topics = "saveTweet", groupId = "group_id")
    public Tweet saveTweet(Tweet tweet) throws Exception  {
        String url = "http://localhost:3003/user/fetch/email="+tweet.getEmail();
//        String url = "http://cardinalis-be.live/user/fetch/email="+tweet.getEmail();
        ResponseEntity<Map> restResponse = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> map = restResponse.getBody();
        Map<String, Object> m = (Map) map.get("data");
        tweet.setUserid(Long.parseLong(m.get("id").toString()));
        tweet.setUsername((String) m.get("username"));
        tweet.setAvatar((String) m.get("avatar"));
        tweet = tweetService.saveTweet(tweet);
        timelineService.saveTweet(tweet);
        System.out.println("hoho " + tweet);
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
//    @KafkaListener(topics = "saveComment", groupId = "group_id")
//    public Comment saveComment(Comment comment) {
//        return commentService.saveComment(comment);
//    }
//
//    @KafkaListener(topics = "saveReply", groupId = "group_id")
//    public Reply saveReply(Reply reply) {
//        return replyService.saveReply(reply);
//    }
//
//    @KafkaListener(topics = "saveFav", groupId = "group_id")
//    public FavoriteTweet saveFav(FavoriteTweet favoriteTweet) {
//        return favoriteTweetService.saveFavorite(favoriteTweet);
//    }
//
//    @KafkaListener(topics = "deleteFav", groupId = "group_id")
//    public FavoriteTweet deleteFav(Long tweetId, String email) {
//        return favoriteTweetService.deleteFavorite(tweetId, email);
//    }

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
//    @KafkaListener(topics = "returnUserInfo", groupId = "group_id")
//    public void listen(String message) throws JsonProcessingException {
//        TweetDTOKafka product = new ObjectMapper().readValue(message, TweetDTOKafka.class);
//        System.out.println("Received Messasge in group - group_id: " + product);
//        Tweet tweet = new Tweet();
//        tweet.setId(product.getId());
//        tweet.setAvatar(product.getAvatar());
//        tweet.setUsername(product.getUsername());
//        tweet.setEmail(product.getEmail());
//        tweet.setUserid(product.getUserid());
//        tweetService.saveTweet(tweet);
//        timelineService.saveTweet(tweet);
//    }
}
