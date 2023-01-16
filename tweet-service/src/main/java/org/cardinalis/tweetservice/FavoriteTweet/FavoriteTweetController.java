package org.cardinalis.tweetservice.FavoriteTweet;
//import org.cardinalis.tweetservice.engine.Producer;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.AuthorizationException;
import org.cardinalis.tweetservice.Tweet.TweetService;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.cardinalis.tweetservice.Util.Reusable.*;


@RestController
@RequestMapping("/tweet")
@AllArgsConstructor
public class FavoriteTweetController {
    @Autowired
    FavoriteTweetService favoriteTweetService;

    @Autowired
    TweetService tweetService;

    private final ModelMapper mapper;


//    @Autowired
//    Producer producer;

    @PostMapping("/favoritetweet")
    public ResponseEntity<Map<String, Object>> saveFav(
            @RequestHeader("Authorization") String token,
            @RequestParam Long tweetId) {
//        producer.send("saveFav", favoriteTweet);
        try {
            String mail = getUserMailFromHeader(token);
            FavoriteTweet fav = FavoriteTweet.builder()
                    .tweet(tweetService.getTweetById(tweetId))
                    .email(mail)
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
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        }  catch (DataIntegrityViolationException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "no tweet with this id");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (AuthorizationException e) {
            System.out.println("cannot saveFav AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot saveFav Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @DeleteMapping("/favoritetweet")
    public ResponseEntity<Map<String, Object>> deleteFav(
            @RequestHeader("Authorization") String token,
            @RequestParam Long tweetId) {
        try {
//            Map<String, Object> message = new HashMap<>();
//            message.put("tweetId", tweetId);
//            message.put("email", email);
//            producer.send("deleteFav", message);
            String mail = getUserMailFromHeader(token);
            FavoriteTweet favoriteTweet = favoriteTweetService.deleteFavorite(tweetId, mail);
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

        } catch (AuthorizationException e) {
            System.out.println("cannot deleteFav AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot deleteFav Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @GetMapping("/favoritetweet")
    public ResponseEntity<Map<String, Object>> getFav(
            @RequestParam Long tweetId,
            @RequestParam String email) {
        try {
            FavoriteTweet favoriteTweet = favoriteTweetService.findFavorite(tweetId, email);
            FavoriteTweetDTO favDTO = mapper.map(favoriteTweet, FavoriteTweetDTO.class);

            Map<String, Object> response = createResponse(HttpStatus.OK, favDTO, "fav found");
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
            return errorResponse(e);
        }
    }

    @GetMapping("/favoritetweets")
    public ResponseEntity<Map<String, Object>> getFavsOfTweet(
            @RequestParam(defaultValue = "") String tweetId,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize) {
        try {
            Map<String, Object> response;
            if(tweetId.isEmpty()) {
                List<FavoriteTweet> favoriteTweets = favoriteTweetService.findFavByTweet(Long.parseLong(tweetId));
                List<FavoriteTweetDTO> favDTOs = favoriteTweets.stream()
                        .map(fav -> mapper.map(fav, FavoriteTweetDTO.class))
                        .collect(Collectors.toList());
                response = createResponse(HttpStatus.OK, favDTOs);

            }
            else {
                Map<String, Object> favedTweets = favoriteTweetService.findFavTweetByUser(email, pageNo, pageSize);
                response = createResponse(HttpStatus.OK, favedTweets);
            }

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
            return errorResponse(e);
        }
    }
}
