package org.cardinalis.tweetservice.controller;
import org.cardinalis.tweetservice.model.FavoriteTweet;
import org.cardinalis.tweetservice.service.FavoriteTweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tweet/favorite")
public class FavoriteTweetController {

    @Autowired
    FavoriteTweetService favoriteTweetService;

    @GetMapping
    public ResponseEntity<ResponseDataContract> listFavoritesByTweet(@RequestParam(value = "tweetId", required = false) UUID tweetId,
                                                                     @RequestParam(value = "username", required = false) String username) {
        List<FavoriteTweet> favoriteTweets;

        if (tweetId == null && username == null)
            throw new IllegalArgumentException();

        if (tweetId != null) {
            favoriteTweets = favoriteTweetService.listFavoritesByTweet(tweetId);
        } else {
            favoriteTweets = favoriteTweetService.listFavoritesByUser(username);
        }
        return ResponseEntity.ok(ResponseDataContract.builder()
                .data(favoriteTweets).build());
    }

    @PostMapping("/{tweet_id}/user/{user_id}")
    public ResponseEntity<ResponseDataContract> favoriteTweetAction(@PathVariable(value = "tweet_id") UUID tweeId,
                                                                    @PathVariable(value = "user_id") String username) {

        FavoriteTweet favoriteTweet = favoriteTweetService.favoriteTweetAction(username, tweeId);
        return ResponseEntity.ok(ResponseDataContract.builder()
                .data(favoriteTweet).build());
    }

    @DeleteMapping("/deleteFavoriteByTweetId")
    public ResponseEntity deleteFavoriteByTweetId(@RequestParam(value = "tweet_id") UUID tweeId,
                                              @RequestParam(value = "user_id") String username) {

        favoriteTweetService.deleteFavoriteByTweetId(tweeId);
        return ResponseEntity.noContent().build();
    }

}
