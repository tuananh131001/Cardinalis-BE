//package com.cardinalis.userservice.kafka;
//
//
//import com.cardinalis.userservice.model.UserEntity;
//import com.cardinalis.userservice.repository.UserRepository;
//import com.cardinalis.userservice.service.UserService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//
//
//@Service
//public class KafkaConsumer {
//
//    @Autowired
//    KafkaProducer kafkaProducer;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserService userService;
//
//    @KafkaListener(topics = "getUser", groupId = "group_id")
//    public void listen(String message) throws JsonProcessingException {
//        TweetDTO tweet = new ObjectMapper().readValue(message, TweetDTO.class);
//        System.out.println("Received Messasge in group - group_id: " + tweet);
//
//        Optional<UserEntity> userEntity = userRepository.findByEmail(tweet.getEmail());
//        if (userEntity.isPresent()) {
//            UserEntity user = userEntity.get();
//            tweet.setEmail(user.getEmail());
//            tweet.setAvatar(user.getAvatar());
//            tweet.setUsername(user.getUsername());
//            tweet.setUserid(user.getId());
//            kafkaProducer.sendBackUserInfo(tweet);
//        }
//    }
//
//
//}
