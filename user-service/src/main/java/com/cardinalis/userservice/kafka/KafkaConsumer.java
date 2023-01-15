//package com.cardinalis.userservice.kafka;
//
//
//import com.cardinalis.userservice.model.UserEntity;
//import com.cardinalis.userservice.repository.UserRepository;
//import com.cardinalis.userservice.repository.projection.user.UserProjection;
//import com.cardinalis.userservice.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.cardinalis.userservice.kafka.UserDTOKafka.mapToUserDTOKafka;
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
//    @KafkaListener(topics = "userProcessUserInfo", groupId = "group_id")
//    public void sendUserInfo(String usermail) throws Exception  {
//        UserEntity user = userRepository.findByEmail(usermail).orElse(null);
//        UserDTOKafka result = mapToUserDTOKafka(user);
//        kafkaProducer.send("returnUserInfo",result);
//    }
//
//    @KafkaListener(topics = "userProcessFollowingList", groupId = "group_id")
//    public void sendFollowingList(String usermail) throws Exception  {
//        UserEntity user = userRepository.findByEmail(usermail).orElse(null);
//        List<UserProjection> followingListPage = userService.getFollowing(user.getId(), PageRequest.of(0, 50)).getContent();
//        List<String> result = followingListPage.stream().map(userProjection -> userProjection.getMail()).collect(Collectors.toList());
//        kafkaProducer.send("returnFollowingList",result);
//    }
//
//}
