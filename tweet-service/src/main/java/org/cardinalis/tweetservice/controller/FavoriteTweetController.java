package org.cardinalis.tweetservice.controller;
//import org.cardinalis.tweetservice.engine.Producer;
import org.cardinalis.tweetservice.exception.NoContentFoundException;
import org.cardinalis.tweetservice.model.FavoriteTweet;
import org.cardinalis.tweetservice.service.FavoriteTweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/favoritetweet")
public class FavoriteTweetController {
    @Autowired
    FavoriteTweetService favoriteTweetService;

//    @Autowired
//    Producer producer;

    @PostMapping("")
    public ResponseEntity<ResponseDataContract> saveFav(@RequestBody FavoriteTweet favoriteTweet) {
//        producer.send("saveFav", favoriteTweet);
        try {
            FavoriteTweet favoritedTweet = favoriteTweetService.saveFavorite(favoriteTweet);
            return ResponseEntity.ok(
                    ResponseDataContract.builder()
                            .status(HttpStatus.OK)
                            .data(favoritedTweet)
                            .message("saved fav")
                            .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                    ResponseDataContract.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .data(null)
                            .message("fav already exists")
                            .build());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ResponseDataContract.builder()
                                    .status(HttpStatus.BAD_REQUEST)
                                    .data(null)
                                    .message("no tweet with this id")
                                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseDataContract.builder()
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .data(null)
                                    .message("error from our internal server")
                                    .build());
        }
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseDataContract> deleteFav(@RequestParam(value = "tweetId") UUID tweetId,
                                                          @RequestParam(value = "username") String username) {
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("tweetId", tweetId);
            message.put("username", username);
//        producer.send("deleteFav", message);
            FavoriteTweet favoriteTweet = favoriteTweetService.deleteFavorite(tweetId, username);
            return ResponseEntity.ok(ResponseDataContract.builder()
                    .status(HttpStatus.OK)
                    .data(favoriteTweet)
                    .message("deleted fav")
                    .build());
        } catch (NoContentFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            ResponseDataContract.builder()
                                    .status(HttpStatus.NOT_FOUND)
                                    .data(null)
                                    .message("fav not found")
                                    .build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseDataContract.builder()
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .data(null)
                                    .message("error from our internal server")
                                    .build());
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseDataContract> getFav(@RequestParam(value = "tweetId") UUID tweetId,
                                                          @RequestParam(value = "username") String username) {
        try {
            FavoriteTweet favoriteTweet = favoriteTweetService.findFavorite(tweetId, username);
            if (favoriteTweet == null) throw new NoContentFoundException("");
            return ResponseEntity.ok(ResponseDataContract.builder()
                    .status(HttpStatus.OK)
                    .data(favoriteTweet)
                    .message(null)
                    .build());
        } catch (NoContentFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ResponseDataContract.builder()
                                    .status(HttpStatus.OK)
                                    .data(null)
                                    .message("fav not found")
                                    .build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseDataContract.builder()
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .data(null)
                                    .message("error from our internal server")
                                    .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataContract> getById(@PathVariable("id") UUID id) {
        try {
            FavoriteTweet favoriteTweet = favoriteTweetService.findFavoriteById(id);
            if (favoriteTweet == null) throw new NoContentFoundException("");
            return ResponseEntity.ok(ResponseDataContract.builder()
                    .status(HttpStatus.OK)
                    .data(favoriteTweet)
                    .message(null)
                    .build());
        } catch (NoContentFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ResponseDataContract.builder()
                                    .status(HttpStatus.OK)
                                    .data(null)
                                    .message("fav not found")
                                    .build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseDataContract.builder()
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .data(null)
                                    .message("error from our internal server")
                                    .build());
        }
    }
}
