package org.cardinalis.tweetservice.FavoriteTweet;
//import org.cardinalis.tweetservice.engine.Producer;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Ultilities.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.cardinalis.tweetservice.Ultilities.Reusable.*;


@RestController
@RequestMapping("/tweet")
public class FavoriteTweetController {
    @Autowired
    FavoriteTweetService favoriteTweetService;

//    @Autowired
//    Producer producer;

    @PostMapping("/favoritetweet")
    public ResponseEntity<Map<String, Object>> saveFav(
            @RequestParam Long tweetId,
            @RequestParam String username) {
//        producer.send("saveFav", favoriteTweet);
        try {
            FavoriteTweet fav = FavoriteTweet.builder()
                    .tweet(Tweet.builder().id(tweetId).build())
                    .username(username)
                    .createdAt(LocalDateTime.now())
                    .build();
            FavoriteTweet favoritedTweet = favoriteTweetService.saveFavorite(fav);
            Map<String, Object> response = createResponse(HttpStatus.OK, favoritedTweet, "saved fav");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "no tweet with this id");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (Exception e) {
            System.out.println("cannot saveFav Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @DeleteMapping("/favoritetweet")
    public ResponseEntity<Map<String, Object>> deleteFav(
            @RequestParam Long tweetId,
            @RequestParam String username) {
        try {
//            Map<String, Object> message = new HashMap<>();
//            message.put("tweetId", tweetId);
//            message.put("username", username);
//            producer.send("deleteFav", message);
            FavoriteTweet favoriteTweet = favoriteTweetService.deleteFavorite(tweetId, username);
            Map<String, Object> response = createResponse(HttpStatus.OK, favoriteTweet, "deleted fav");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "fav not found");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot deleteFav IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (Exception e) {
            System.out.println("cannot deleteFav Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @GetMapping("/favoritetweet")
    public ResponseEntity<Map<String, Object>> getFav(
            @RequestParam(defaultValue = "") String tweetId,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String id) {
        try {
            FavoriteTweet favoriteTweet;
            if (!id.isEmpty()) {
                favoriteTweet = favoriteTweetService.findFavoriteById(Long.parseLong(id));
            }
            else {
                favoriteTweet = favoriteTweetService.findFavorite(Long.parseLong(tweetId), username);
            }
            Map<String, Object> response = createResponse(HttpStatus.OK, favoriteTweet, "fav found");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
                Map<String, Object> response = createResponse(HttpStatus.OK, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getFav IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        }  catch (Exception e) {e.printStackTrace();
            System.out.println("cannot getFav Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @GetMapping("/favoritetweets")
    public ResponseEntity<Map<String, Object>> getFavsOfTweet(
            @RequestParam(defaultValue = "") String tweetId) {
        try {
            List<FavoriteTweet> favoriteTweets = favoriteTweetService.findAllFavoritesOfTweet(Long.parseLong(tweetId));
            Map<String, Object> response = createResponse(HttpStatus.OK, favoriteTweets, "fav found");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.OK, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getFav IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        }  catch (Exception e) {e.printStackTrace();
            System.out.println("cannot getFav Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }
}
