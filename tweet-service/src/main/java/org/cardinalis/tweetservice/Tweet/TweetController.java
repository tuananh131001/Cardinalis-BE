package org.cardinalis.tweetservice.Tweet;


import org.apache.kafka.common.errors.AuthorizationException;
import org.cardinalis.tweetservice.Comment.CommentRepository;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetRepository;
import org.cardinalis.tweetservice.Util.NoContentFoundException;

import org.cardinalis.tweetservice.Kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.cardinalis.tweetservice.Util.Reusable.*;


@RestController
@RequestMapping("")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FavoriteTweetRepository favoriteTweetRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

//    @Autowired
//    private Producer producer;

//    private final ModelMapper mapper;

    @Autowired
    TweetDTOService tweetDTOService;


    @PostMapping(path = "/tweet")
    public ResponseEntity<Map<String, Object>> saveTweet(
            @RequestHeader("Authorization") String token,
            @RequestBody Tweet tweet) {
        try {
            String mail = getUserMailFromHeader(token);
            tweet.setEmail(mail);
            if (tweet.getCreatedAt() == null) tweet.setCreatedAt(LocalDateTime.now());
            kafkaProducer.send("saveTweet", tweet);
//            tweet = tweetService.saveTweet(tweet);
            Map<String, Object> response = createResponse(HttpStatus.OK, tweet, "saved tweet");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            System.out.println("cannot addTweet IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (AuthorizationException e) {
            System.out.println("cannot addTweet AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot addTweet Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }


    @PutMapping("/tweet")
    public ResponseEntity<Map<String, Object>> editTweet(
            @RequestHeader("Authorization") String token,
            @RequestBody Tweet tweet) {
        try {
            String mail = getUserMailFromHeader(token);
            tweet.setEmail(mail);
            Tweet tweetEdited = tweetService.editTweet(tweet);
            Map<String, Object> response = createResponse(HttpStatus.OK, tweetEdited, "saved comment");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot editTweet IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);
        } catch (DataIntegrityViolationException e) {
            System.out.println("cannot editTweet DataIntegrityViolationException: " + e.getMessage());
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "no tweet with this id");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        }  catch (AuthorizationException e) {
            System.out.println("cannot editTweet AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (Exception e) {
            System.out.println("cannot saveComment Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @GetMapping(path = "/tweet")
    public ResponseEntity<Map<String, Object>> getTweetById(
            @RequestParam Long id,
            @RequestParam(defaultValue = "true") Boolean needCount) {
        try {
            Tweet tweet = tweetService.getTweetById(id);
            Object result = needCount ? tweetDTOService.mapTweetTweetDTO(tweet) : tweet;
            Map<String, Object> response = createResponse(HttpStatus.OK, result, "tweet found");
            return ResponseEntity.ok(response);

        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(
                    HttpStatus.OK, null, "no tweet found");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getTweetById IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (Exception e) {
            System.out.println("cannot getTweetById Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @DeleteMapping(path = "/tweet")
    public ResponseEntity<Map<String, Object>> deleteTweet(
            @RequestHeader("Authorization") String token,
            @RequestParam Long id) {
        try {
            String mail = getUserMailFromHeader(token);
            Tweet tweet = tweetService.getTweetById(id);
            if (!mail.equals(tweet.getEmail())) throw new AuthorizationException("unauthorized user");
            tweetService.deleteTweet(id);
            Map<String, Object> response = createResponse(HttpStatus.OK, new TweetDTO(tweet), "deleted tweet");
            return ResponseEntity.ok(response);

        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(
                    HttpStatus.NOT_IMPLEMENTED, null, "delete failed - no tweet found");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);

        } catch (IllegalArgumentException e) {
            System.out.println("cannot deleteTweet IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (AuthorizationException e) {
            System.out.println("cannot deleteTweet AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot deleteTweet Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @GetMapping(path = "/tweets")
    public ResponseEntity<Map<String, Object>> getTweets(
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "true") Boolean needCount,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize) {
        try {
            Map<String, Object> result;
            if (!email.isEmpty()) {
                result = tweetService.getNewestTweetsFromUser(email, needCount, pageNo, pageSize);
            }
            else {
                result = tweetService.getAll(needCount, pageNo, pageSize);
            }

            Map<String, Object> response = createResponse(HttpStatus.OK, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getTweets IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (Exception e) {
            System.out.println("cannot getTweets Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

}