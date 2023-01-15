package org.cardinalis.tweetservice.Timeline;

//import com.cardinalis.userservice.model.UserEntity;
//import com.cardinalis.userservice.repository.UserRepository;
//import com.cardinalis.userservice.repository.projection.user.UserProjection;
//import com.cardinalis.userservice.service.UserService;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetServiceImpl;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

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

//    @Autowired
//    UserService userService;

    @Autowired
    TweetServiceImpl tweetService;

//    @Autowired
//    UserRepository userRepository;

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    private String createKey(Tweet tweet) {
        return tweet.getUsermail() + "::" + tweet.getId();
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
    public Map<String, Object> getAll(int pageNo, int pageSize) {
        List<Tweet> tweets = hashOperations.entries(TIMELINE_CACHE).values().stream().collect(Collectors.toList());
        return createPageResponse(tweets, 0, false, 1, tweets.size(), tweets.size());
    }

//    @Cacheable(value = "TIMELINE")
//    public Map<String, Object> getTimelineForUser(String usermail, int pageNo, int pageSize) throws Exception{
//        List<Tweet> userTimeline = new ArrayList<>();
//
//        UserEntity user = userRepository.findByEmail(usermail).orElseThrow(() -> new NoContentFoundException("not found user"));
//        Page<UserProjection> fPage = userService.getFollowing(user.getId(), PageRequest.of(0, 50));
//        List<String> followingList = fPage.getContent().stream().map(userProjection -> userProjection.getMail()).collect(Collectors.toList());
//        if (followingList == null || followingList.isEmpty()) {
//            throw new NoContentFoundException("no following");
//        }
//
//        for (String followedMail : followingList) {
//            String keyPattern = followedMail + "::" + "*";
//            Map<String, Tweet> map = findTweetByPattern(keyPattern);
//            userTimeline.addAll(map.values());
//        }
//
//        if (userTimeline.isEmpty()) {
//            for (String followedMail : followingList) {
//                Map<String, Object> page = tweetService.getNewestTweetsFromUser(followedMail, true, 0, 10);
//                List<Tweet> tweets = (ArrayList) page.get("data");
//                userTimeline.addAll(tweets);
//            }
//        }
//        if (!userTimeline.isEmpty()) {
//            Collections.sort(userTimeline);
//            Collections.reverse(userTimeline);
//        }
//        return createPageResponse(userTimeline, 0, false, 1, userTimeline.size(), userTimeline.size());
//    }

}
