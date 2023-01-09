package org.cardinalis.tweetservice.Tweet;

import lombok.AllArgsConstructor;
import org.cardinalis.tweetservice.Comment.CommentRepository;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetRepository;
import org.cardinalis.tweetservice.Ultilities.NoContentFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.cardinalis.tweetservice.Ultilities.Reusable.*;


@RestController
@RequestMapping("")
@AllArgsConstructor
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FavoriteTweetRepository favoriteTweetRepository;

//    @Autowired
//    private Producer producer;

    private final ModelMapper mapper;

    @PostMapping(path = "/tweet")
    public ResponseEntity<Map<String, Object>> addTweet(@RequestBody Tweet tweet) {
        try {
            System.out.println(tweet);
            if (tweet.getCreatedAt() == null) tweet.setCreatedAt(LocalDateTime.now());
//            producer.send("saveTweet", tweet);
            tweet = tweetService.saveTweet(tweet);
            Map<String, Object> response = createResponse(HttpStatus.OK, tweet, "saved tweet");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            System.out.println("cannot addTweet IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (Exception e) {
            System.out.println("cannot addTweet Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @GetMapping(path = "/tweet")
    public ResponseEntity<Map<String, Object>> getTweetById(
            @RequestParam Long id,
            @RequestParam(defaultValue = "true") Boolean needCount) {
        try {
            Tweet tweet = tweetService.getTweetById(id);
            TweetDTO tweetDTO = mapper.map(tweet, TweetDTO.class);
            if (needCount) setCount(tweetDTO);
            Map<String, Object> response = createResponse(HttpStatus.OK, tweetDTO, "tweet found");
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
            return internalErrorResponse(e);
        }
    }

    @DeleteMapping(path = "/tweet")
    public ResponseEntity<Map<String, Object>> deleteTweet(@RequestParam Long id) {
        try {
            Tweet tweet = tweetService.deleteTweet(id);
            Map<String, Object> response = createResponse(HttpStatus.OK, tweet, "deleted tweet");
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

        } catch (Exception e) {
            System.out.println("cannot deleteTweet Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @GetMapping(path = "/tweets")
    public ResponseEntity<Map<String, Object>> getTweets(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "true") Boolean needCount,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize) {
        try {
            Map<String, Object> result;
            if (!username.isEmpty()) {
                result = tweetService.getNewestTweetsFromUser(username, needCount, pageNo, pageSize);
            }
            else {
                result = tweetService.getAll(needCount, pageNo, pageSize);
            }

            Map<String, Object> response = createResponse(HttpStatus.OK, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getNewestTweetsFromUser IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (Exception e) {
            System.out.println("cannot getNewestTweetsFromUser Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    public TweetDTO setCount(TweetDTO tweetDTO) {
        tweetDTO.setTotalFav(favoriteTweetRepository.countByTweet_Id(tweetDTO.getId()));
        tweetDTO.setTotalComment(commentRepository.countByTweet_Id(tweetDTO.getId()));
        return tweetDTO;
    }
}