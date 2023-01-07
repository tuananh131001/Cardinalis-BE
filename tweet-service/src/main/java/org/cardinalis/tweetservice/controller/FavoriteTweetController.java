//package org.cardinalis.tweetservice.controller;
//import org.cardinalis.tweetservice.engine.Producer;
//import org.cardinalis.tweetservice.model.FavoriteTweet;
//import org.cardinalis.tweetservice.service.FavoriteTweetService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/tweet/favorite")
//public class FavoriteTweetController {
//
//    @Autowired
//    FavoriteTweetService favoriteTweetService;
//
//    @Autowired
//    Producer producer;
//
////    @GetMapping("")
////    public ResponseEntity<ResponseDataContract> listFavoritesByTweet(@RequestParam(value = "tweetId", required = false) UUID tweetId,
////                                                                     @RequestParam(value = "username", required = false) String username) {
////        List<FavoriteTweet> favoriteTweets;
////
////        if (tweetId == null && username == null)
////            throw new IllegalArgumentException();
////
////        if (tweetId != null) {
////            favoriteTweets = favoriteTweetService.listFavoritesByTweet(tweetId);
////        } else {
////            favoriteTweets = favoriteTweetService.listFavoritesByUser(username);
////        }
////        return ResponseEntity.ok(ResponseDataContract.builder()
////                .data(favoriteTweets).build());
////    }
//
//    @PostMapping("/saveFavorite")
//    public ResponseEntity<ResponseDataContract> saveFav(@RequestBody FavoriteTweet favoriteTweet) {
//        producer.send("saveFav", favoriteTweet);
//
////        FavoriteTweet favoritedTweet = favoriteTweetService.saveFavorite(favoriteTweet);
//        return ResponseEntity.ok(ResponseDataContract.builder()
//                .data(favoriteTweet).build());
//    }
//
//    @PostMapping("/deleteFavorite")
//    public ResponseEntity<ResponseDataContract> deleteFav(@RequestParam(value = "tweetId") UUID tweeId,
//                                                          @RequestParam(value = "username") String username) {
//        Map<String, Object> message = new HashMap<>();
//        message.put("tweeId", tweeId);
//        message.put("username", username);
//        producer.send("saveFav", message);
////        FavoriteTweet favoriteTweet = favoriteTweetService.deleteFavorite(tweeId, username);
//        return ResponseEntity.ok(ResponseDataContract.builder()
//                .data(null).build());
//    }
//
//    @DeleteMapping("/deleteFavoritesByTweetId")
//    public ResponseEntity deleteFavoritesByTweetId(@RequestParam(value = "tweet_id") UUID tweeId) {
//
//        favoriteTweetService.deleteFavoritesByTweetId(tweeId);
//        return ResponseEntity.noContent().build();
//    }
//
//}
