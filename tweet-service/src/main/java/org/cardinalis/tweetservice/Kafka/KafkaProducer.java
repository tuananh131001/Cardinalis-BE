package org.cardinalis.tweetservice.Kafka;//package org.cardinalis.tweetservice.engine;
//
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetDTO;
import org.cardinalis.tweetservice.Tweet.TweetDTOKafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String TOPIC_TWEET = "tweet";
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

//    @Bean


    public void sendTweetKafka(Tweet tweet) throws JsonProcessingException {
        kafkaTemplate.send(TOPIC_TWEET, new ObjectMapper().writeValueAsString(tweet));
    }
}
