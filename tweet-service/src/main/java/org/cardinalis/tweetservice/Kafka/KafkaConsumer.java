package org.cardinalis.tweetservice.Kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cardinalis.tweetservice.Comment.CommentService;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetService;
import org.cardinalis.tweetservice.ReplyComment.ReplyService;
import org.cardinalis.tweetservice.Timeline.TimelineService;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetDTOKafka;
import org.cardinalis.tweetservice.Tweet.TweetRepository;
import org.cardinalis.tweetservice.Tweet.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class KafkaConsumer {

    @Autowired
    TweetRepository tweetRepository;

//    @Autowired
//    KafkaProducer kafkaProducer;
    @Autowired
    RestTemplate restTemplate;


    @KafkaListener(topics = "tweet", groupId = "group_id")
    public void listen(String message) throws JsonProcessingException {
        Tweet product = new ObjectMapper().readValue(message, Tweet.class);
        tweetRepository.save(product);
    }
}
