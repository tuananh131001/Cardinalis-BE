package org.cardinalis.tweetservice.Timeline;

import org.cardinalis.tweetservice.DTOUser.UserResponse;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetRepository;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

import static org.cardinalis.tweetservice.Util.Reusable.*;

@RestController
@RequestMapping(value = "/tweet/timeline")
public class TimelineController {
    private static final Logger LOG = LoggerFactory.getLogger(TimelineController.class);

    @Autowired
    TimelineService timelineService;
    @Autowired
    TweetRepository tweetRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, List<Tweet>> hashOperations;
    private final String TIMELINE_CACHE = "TIMELINE";

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }
    private ArrayList<Map<String, Object>> getAllFollowers(Long myId, String accessToken){
        System.out.println("myId = " + myId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",accessToken);

        HttpEntity<String> entity = new HttpEntity<String>("",headers);
        ResponseEntity<Map> user =  restTemplate.exchange("/following?userId=" + myId, HttpMethod.GET,entity, Map.class);
        System.out.println("user = " + user);
        Map userDataNew = user.getBody();
        System.out.println("userDataNew = " + userDataNew);
        ArrayList<Map<String, Object>> userData = (ArrayList<Map<String, Object>>) user.getBody().get("data");

//        List<Map<String, Object>> followers = (List) userData.get("followers");
        System.out.println("followers: " + userData);
        return userData; //return all tweet of user following
    }
    private List<Tweet> getTweetsOfFollowing(ArrayList<Map<String, Object>> followers, String accessToken){

        List<Tweet> tweets = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        System.out.println("followers = " + followers);
        for (Map<String, Object> follower : followers) {
            String userEmail = (String) follower.get("email");
            tweetRepository.findAllByEmail(userEmail,pageRequest).forEach(tweet -> {
                System.out.println("tweet = " + tweet);
                tweets.add(tweet);
            });
        }
        System.out.println("tweets = " + tweets);
        return tweets;
    }
    @GetMapping("")
    public ResponseEntity<List<Tweet>> getUserTimeline(
            @RequestParam(defaultValue = "") String userId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize,
      @RequestHeader("Authorization") String token){
        try {
            List<Tweet> listTweetInCache = hashOperations.get(TIMELINE_CACHE, userId);
            if (listTweetInCache != null) {
                return new ResponseEntity<>(listTweetInCache, HttpStatus.OK);
            }



            String mail = getUserMailFromHeader(token);
            Map user =  restTemplate.getForObject("/fetch/email=" + mail, Map.class);
            assert user != null;
            Map<String, Object> userData = (Map) user.get("data");
            Long myUserId = Long.valueOf((Integer) userData.get("id"));
            ArrayList<Map<String, Object>> listOfFollowing= getAllFollowers(myUserId,token);
            List<Tweet> tweetsGet = getTweetsOfFollowing(listOfFollowing,token);
            hashOperations.put(TIMELINE_CACHE, userId, tweetsGet);
            Map<String,Object> response = new HashMap<>();
            return new ResponseEntity<List<Tweet>>( tweetsGet , HttpStatus.OK);

        }   catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
