package org.cardinalis.tweetservice.FavoriteTweet;
//import org.cardinalis.tweetservice.engine.Producer;
import org.cardinalis.tweetservice.Ultilities.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.cardinalis.tweetservice.Ultilities.Reusable.*;


@RestController
@RequestMapping("/favoritetweet")
public class FavoriteTweetController {
    @Autowired
    FavoriteTweetService favoriteTweetService;

//    @Autowired
//    Producer producer;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> saveFav(@RequestBody FavoriteTweet favoriteTweet) {
//        producer.send("saveFav", favoriteTweet);
        try {
            FavoriteTweet favoritedTweet = favoriteTweetService.saveFavorite(favoriteTweet);
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

    @DeleteMapping("")
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

    @GetMapping("")
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
}
