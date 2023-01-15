package com.cardinalis.userservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String TOPIC_RETURN_USER = "returnUserInfo";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendBackUserInfo(TweetDTO user) throws JsonProcessingException {
        logger.info(String.format("#### -> Producing message -> %s", user));
        this.kafkaTemplate.send(TOPIC_RETURN_USER, new ObjectMapper().writeValueAsString(user));
    }

}
