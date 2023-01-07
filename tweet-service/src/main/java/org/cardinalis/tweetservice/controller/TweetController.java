package org.cardinalis.tweetservice.controller;

import lombok.AllArgsConstructor;
//import org.cardinalis.tweetservice.engine.Producer;
import org.cardinalis.tweetservice.exception.NoContentFoundException;
import org.cardinalis.tweetservice.model.Tweet;
import org.cardinalis.tweetservice.model.TweetDTO;
import org.cardinalis.tweetservice.service.TweetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/tweet")
@AllArgsConstructor
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @Autowired
//    private Producer producer;
    private final ModelMapper mapper;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addTweet(@RequestBody Tweet tweet){
        try {
            if (tweet.getCreatedAt() == null) tweet.setCreatedAt(LocalDateTime.now());
//            producer.send("saveTweet", tweet);
            tweet = tweetService.saveTweet(tweet);
            TweetDTO tweetDTO = mapper.map(tweet, TweetDTO.class);
            Map<String, Object> response = createResponse(HttpStatus.OK, tweetDTO, "saved tweet");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> response = createResponse(HttpStatus.BAD_REQUEST);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);

        }  catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "an error occurs - cannot save tweet");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize)
    {
        try {
            Map<String, Object> result = tweetService.getAll(pageNo, pageSize);
            Map<String, Object> response = createResponse(HttpStatus.OK, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = createResponse(HttpStatus.BAD_REQUEST);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }  catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "an error occurs - cannot get tweets");

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getTweetById(@PathVariable(value = "id") UUID id){
        try {
            Tweet tweet = tweetService.getTweetById(id);
            TweetDTO tweetDTO = mapper.map(tweet, TweetDTO.class);
            Map<String, Object> response = createResponse(HttpStatus.OK, tweetDTO);
            return ResponseEntity.ok(response);

        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(
                    HttpStatus.OK,
                    null, "no tweet is found"
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = createResponse(HttpStatus.BAD_REQUEST);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);

        }  catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "an error occurs - cannot get tweet"
            );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deleteTweet(@PathVariable(value = "id") UUID id){
        try {
            Tweet tweet = tweetService.deleteTweet(id);
            TweetDTO tweetDTO = mapper.map(tweet, TweetDTO.class);
            Map<String, Object> response = createResponse(HttpStatus.OK, tweetDTO, "deleted tweet");
            return ResponseEntity.ok(response);

        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(
                    HttpStatus.OK,
                    null,
                    "delete unsuccessfully - no tweet is found");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> response = createResponse(HttpStatus.BAD_REQUEST);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "an error occurs - cannot delete tweet"
            );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getNewestTweetsFromUser(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            Map<String, Object> result = tweetService.getNewestTweetsFromUser(username, pageNo, pageSize);
            Map<String, Object> response = createResponse(HttpStatus.OK, result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "an error occurs - cannot get tweets from username = " + username
            );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    private Map<String, Object> createResponse(HttpStatus status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", status.is2xxSuccessful());
        response.put("code", status.value());
        response.put("data", data);
        response.put("message", message);
        return response;
    }
    private Map<String, Object> createResponse(HttpStatus status, Object data) {
        return createResponse(status, data, null);
    }

    private Map<String, Object> createResponse(HttpStatus status) {
        return createResponse(status, null, null);
    }


}
