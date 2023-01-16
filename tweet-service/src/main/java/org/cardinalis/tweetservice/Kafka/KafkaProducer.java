package org.cardinalis.tweetservice.Kafka;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final String TOPIC_GET_USER = "getUser";


    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    public NewTopic saveTweet() {
        return new NewTopic("saveTweet", 1, (short) 1);
    }

    @Bean
    public NewTopic saveComment() {
        return new NewTopic("saveComment", 1, (short) 1);
    }

    @Bean
    public NewTopic saveReply() {
        return new NewTopic("saveReply", 1, (short) 1);
    }

    @Bean
    public NewTopic saveFav() {
        return new NewTopic("saveFav", 1, (short) 1);
    }

    @Bean
    public NewTopic deleteFav() {
        return new NewTopic("deleteFav", 1, (short) 1);
    }


    public void send(String topic, Object object) throws Exception {
        logger.info(String.format("#### -> Producing kafka message for topic: " + topic));
        kafkaTemplate.send(topic, object);
    }
}

