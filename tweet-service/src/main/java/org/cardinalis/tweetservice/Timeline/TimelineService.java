package org.cardinalis.tweetservice.Timeline;

//import com.cardinalis.userservice.model.UserEntity;
//import com.cardinalis.userservice.repository.UserRepository;
//import com.cardinalis.userservice.repository.projection.user.UserProjection;
//import com.cardinalis.userservice.service.UserService;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetServiceImpl;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.cardinalis.tweetservice.DTO.SuccessResponseDTO;
import org.cardinalis.tweetservice.DTO.UserResponse;
import org.cardinalis.tweetservice.Util.Reusable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static org.cardinalis.tweetservice.Util.Reusable.createPageResponse;

//@Transactional
@Service
public class
TimelineService {

    private final String TIMELINE_CACHE = "TIMELINE";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Tweet> hashOperations;
    @Autowired
    TweetServiceImpl tweetService;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Reusable reusable;

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    private String createKey(Tweet tweet) {
        return tweet.getEmail() + "::" + tweet.getId();
    }

    @Cacheable(value = "TIMELINE")
    public void saveTweet(Tweet tweet) {
        hashOperations.put(TIMELINE_CACHE, createKey(tweet), tweet);
    }

    @Cacheable(value = "TIMELINE")
    public Tweet getTweet(String key) {
        return hashOperations.get(TIMELINE_CACHE, key);
    }

    @Cacheable(value = "TIMELINE")
    public void deleteTweet(Long id) throws Exception {
        String keyPattern = "*" + "::" + id;
        Map<String, Tweet> found = findTweetByPattern(keyPattern);
        if (found.size() == 0 || found == null) {
            throw new NoContentFoundException("No tweet found");
        }
        else {
            for (String key: found.keySet()) {
                hashOperations.delete(TIMELINE_CACHE, key);
            }
        }
    }

    @Cacheable(value = "TIMELINE")
    public Map<String, Tweet> findTweetByPattern(String keyPattern) {
        ScanOptions scanOptions = ScanOptions.scanOptions().match(keyPattern).count(20).build();
        Cursor<Map.Entry<String, Tweet>> c = hashOperations.scan(TIMELINE_CACHE, scanOptions);
        Map<String, Tweet> result = new HashMap<>();
        while (c.hasNext()) {
            Map.Entry<String, Tweet> e = c.next();
            result.put(e.getKey(), e.getValue());
        }
        return result;
    }

    @Cacheable(value = "TIMELINE")
    public Map<String, Object> getAll(int pageNo, int pageSize) throws Exception {
        List<Tweet> tweets = hashOperations.entries(TIMELINE_CACHE).values().stream().collect(Collectors.toList());
        return createPageResponse(reusable.getTweetDTOList(tweets), 0, false, 1, tweets.size(), tweets.size());
    }

    @Cacheable(value = "TIMELINE")
    public Map<String, Object> getTimelineForUser(Long userId, int pageNo, int pageSize) throws Exception{
        List<Tweet> userTimeline = new ArrayList<>();
        String url = "http://localhost:3003/user/following/"+userId;
//        String url = "http://cardinalis-be.live/user/following/"+userId;
        ResponseEntity<SuccessResponseDTO> restResponse = restTemplate.getForEntity(url, SuccessResponseDTO.class);
        SuccessResponseDTO res = restResponse.getBody();

        List<UserResponse> followingList = (ArrayList) res.getData();

        if (followingList == null || followingList.isEmpty()) {
            throw new NoContentFoundException("no following");
        }

        for (UserResponse user : followingList) {
            String keyPattern = user.getEmail() + "::" + "*";
            Map<String, Tweet> map = findTweetByPattern(keyPattern);
            userTimeline.addAll(map.values());
        }

        if (userTimeline.isEmpty()) {
            for (UserResponse user : followingList) {
                Map<String, Object> page = tweetService.getNewestTweetsFromUser(user.getEmail(), 0, 10);
                List<Tweet> tweets = (ArrayList) page.get("data");
                userTimeline.addAll(tweets);
            }
        }
        if (!userTimeline.isEmpty()) {
            Collections.sort(userTimeline);
            Collections.reverse(userTimeline);
        }
        return createPageResponse(reusable.getTweetDTOList(userTimeline), 0, false, 1, userTimeline.size(), userTimeline.size());
    }

}
