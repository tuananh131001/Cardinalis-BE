//package org.cardinalis.tweetservice.engine;
//
//import org.cardinalis.tweetservice.model.FavoriteTweet;
//import org.cardinalis.tweetservice.model.Tweet;
//import org.cardinalis.tweetservice.service.FavoriteTweetService;
//import org.cardinalis.tweetservice.service.TweetService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//public class Consumer {
//    @Autowired
//    TweetService tweetService;
//
//
//    @Autowired
//    FavoriteTweetService favoriteTweetService;
//
//    @KafkaListener(topics = "saveTweet", groupId = "group_id")
//    public void saveTweet(Tweet tweet) throws Exception  {
//        tweetService.saveTweet(tweet);
//    }
//
//    @KafkaListener(topics = "deleteTweet", groupId = "group_id")
//    public Tweet deleteTweet(UUID id) throws Exception  {
//        return tweetService.deleteTweet(id);
//    }
//
//    @KafkaListener(topics = "saveFav", groupId = "group_id")
//    public FavoriteTweet saveFav(FavoriteTweet favoriteTweet) {
//        return favoriteTweetService.saveFavorite(favoriteTweet);
//    }
//
//    @KafkaListener(topics = "deleteFav", groupId = "group_id")
//    public FavoriteTweet deleteFav(UUID tweetId, String username) {
//        return favoriteTweetService.deleteFavorite(tweetId, username);
//    }
//
////    @KafkaListener(topics = "getTweetById", groupId = "group_id")
////    public Tweet getTweetById(UUID id) throws Exception {
////        return tweetService.getTweetById(id);
////    }
////
////    @KafkaListener(topics = "getNewestTweetsFromUser", groupId = "group_id")
////    public List<Tweet> getNewestTweetsFromUser(String username, int pageNo, int pageSize) throws Exception {
////        return tweetService.getNewestTweetsFromUser(username, pageNo, pageSize);
////    }
//}
