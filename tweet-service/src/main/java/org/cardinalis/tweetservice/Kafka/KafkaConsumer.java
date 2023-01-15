package org.cardinalis.tweetservice.Kafka;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cardinalis.tweetservice.Comment.Comment;
import org.cardinalis.tweetservice.Comment.CommentService;
import org.cardinalis.tweetservice.DTOUser.UserDTOKafka;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetService;
import org.cardinalis.tweetservice.ReplyComment.Reply;
import org.cardinalis.tweetservice.ReplyComment.ReplyService;
import org.cardinalis.tweetservice.Timeline.TimelineService;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetDTO;
import org.cardinalis.tweetservice.Tweet.TweetDTOKafka;
import org.cardinalis.tweetservice.Tweet.TweetService;
import org.cardinalis.tweetservice.DTOUser.AuthUserResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.cardinalis.tweetservice.Util.Reusable.importUserInfo;


@Service
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private TweetService tweetService;

//    @KafkaListener(topics = "users", groupId = "group_id")
//    public void consume(String message) throws IOException {
//        logger.info(String.format("#### -> Consumed message -> %s", message));
//
//        //save message to database
//        City city = new City(message);
//
//        this.cityRepository.save(city);
//
//        logger.info(String.format("#### -> ID message -> %s", city.getId()));
//
//    }
    @KafkaListener(topics = "returnUserInfo", groupId = "group_id")
    public void listen(String message) throws JsonProcessingException {
        TweetDTOKafka product = new ObjectMapper().readValue(message, TweetDTOKafka.class);
        System.out.println("Received Messasge in group - group_id: " + product);
        Tweet tweet = new Tweet();
        tweet.setId(product.getId());
        tweet.setAvatar(product.getAvatar());
        tweet.setUsername(product.getUsername());
        tweet.setEmail(product.getEmail());
        tweet.setUserid(product.getUserid());
        tweetService.saveTweet(tweet);

    }
}
