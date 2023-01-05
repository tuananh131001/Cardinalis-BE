//package org.cardinalis.tweetservice.engine;
//
//import org.cardinalis.tweetservice.exception.NoContentFoundException;
//import org.cardinalis.tweetservice.model.Tweet;
//import org.cardinalis.tweetservice.repository.TweetRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.TopicPartition;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class Consumer {
//
//    private final Logger logger = LoggerFactory.getLogger(Consumer.class);
//
//    @Autowired
//    TweetRepository tweetRepository;
//
//    private static final String TOPIC = "tweetservice";
//
//    @KafkaListener(topicPartitions = @TopicPartition(
//            topic = TOPIC, partitions = {"0"}), groupId = "group_id")
//    public void saveTweet(Tweet tweet) throws Exception {
//        try {
//            tweetRepository.save(tweet);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//
//    @KafkaListener(topicPartitions = @TopicPartition(
//            topic = TOPIC, partitions = {"1"}), groupId = "group_id")
//    public Tweet getTweetById(UUID id) throws IOException  {
//        return tweetRepository.findById(id)
//                .orElseThrow(() -> new NoContentFoundException("Tweet not found for id = " + id));
//    }
//
//    @KafkaListener(topicPartitions = @TopicPartition(
//            topic = TOPIC, partitions = {"2"}), groupId = "group_id")
//    public List<Tweet> getNewestTweetsFromUser(String username, int size) throws Exception {
//        try {
//            List<Tweet> result;
//            Pageable pageable = PageRequest.of(0, size, Sort.Direction.DESC,"createdAt");
//            Page<Tweet> page = tweetRepository.findByUsernameOrderByCreatedAtDesc(username, pageable);
//            result = page.getContent();
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    @KafkaListener(topicPartitions = @TopicPartition(
//            topic = TOPIC, partitions = {"3"}), groupId = "group_id")
//    public Tweet deleteTweet(UUID id) throws Exception {
//        try {
//            Tweet tweet = getTweetById(id);
//            tweetRepository.delete(tweet);
//            return tweet;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
////
//    @KafkaListener(topicPartitions = @TopicPartition(
//            topic = TOPIC, partitions = {"4"}), groupId = "group_id")
//    public List<Tweet> getAll(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
//        Page<Tweet> result = tweetRepository.findAll(pageable);
//        return result.getContent();
//    }
//}
