package org.cardinalis.tweetservice.engine;//package org.cardinalis.tweetservice.engine;
//
//import org.apache.kafka.clients.admin.NewTopic;
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
//
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    @Bean
//    public NewTopic saveTweet() {
//        return new NewTopic("saveTweet", 1, (short) 1);
//    }
//
//    @Bean
//    public NewTopic deleteTweet() {
//        return new NewTopic("deleteTweet", 1, (short) 1);
//    }
//
//    @Bean
//    public NewTopic saveFav() {
//        return new NewTopic("saveFav", 1, (short) 1);
//    }
//
//    @Bean
//    public NewTopic deleteFav() {
//        return new NewTopic("deleteFav", 1, (short) 1);
//    }
//
//    public void send(String topic, Object object) {
//        logger.info(String.format("#### -> Producing kafka message for topic: " + topic));
//        kafkaTemplate.send(topic, object);
//    }
//}
