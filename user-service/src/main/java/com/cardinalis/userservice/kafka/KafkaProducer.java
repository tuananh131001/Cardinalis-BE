//package com.cardinalis.userservice.kafka;//package org.cardinalis.tweetservice.engine;
////
//import org.apache.kafka.clients.admin.NewTopic;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class KafkaProducer {
//
//    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
//
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    @Bean
//    public NewTopic returnUserInfo() {
//        return new NewTopic("returnUserInfo", 1, (short) 1);
//    }
//
//    @Bean
//    public NewTopic returnFollowingList() {
//        return new NewTopic("returnFollowingList", 1, (short) 1);
//    }
//
//    public void send(String topic, Object object) {
//        logger.info(String.format("#### -> Producing kafka message for topic: " + topic));
//        kafkaTemplate.send(topic, object);
//    }
//}
