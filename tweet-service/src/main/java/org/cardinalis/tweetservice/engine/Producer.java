//package org.cardinalis.tweetservice.engine;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.cardinalis.tweetservice.model.Tweet;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class Producer {
//
//    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
//    private static final String TOPIC = "tweetservice";
//
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    @Bean
//    public NewTopic tweetServiceTopic() {
//        return new NewTopic(TOPIC, 6, (short) 1);
//    }
//
//    void send(int partition, String message) {
//        kafkaTemplate.send(TOPIC, partition, null, message);
//    }
//}
