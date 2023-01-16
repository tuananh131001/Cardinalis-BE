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

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String TOPIC_GET_USER = "getUser";
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

//    @Bean


    public void sendMessageGetUser(Tweet message) throws JsonProcessingException {
        logger.info(String.format("#### -> Producing message -> %s", message));
        // tweet to tweetdto
        TweetDTOKafka tweetDTO = new TweetDTOKafka();
        tweetDTO.setId(message.getId());
        tweetDTO.setContent(message.getContent());
        tweetDTO.setEmail(message.getEmail());
        tweetDTO.setAvatar(message.getAvatar());
        tweetDTO.setUsername(message.getUsername());
        tweetDTO.setUserid(message.getUserid());

        this.kafkaTemplate.send(TOPIC_GET_USER, new ObjectMapper().writeValueAsString(tweetDTO));

    }
}
