package com.cardinalis.timelineservice.service;

import com.cardinalis.timelineservice.exception.NoContentFoundException;
import com.cardinalis.timelineservice.model.Timeline;
import com.cardinalis.timelineservice.repository.TimelineRepository;
import com.cardinalis.userservice.service.UserService;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

//@Transactional
@Service
public class
TimelineService{
    private final String TIMELINE_CACHE = "TIMELINE";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Tweet> hashOperations;

    @Autowired
    UserService userService;

    @Autowired
    TweetServiceImpl tweetService;

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    private String createKey(Tweet tweet) {
        return tweet.getUsername() + "::" + tweet.getId();
    }

//    @KafkaListener(topics = "saveTweet", groupId = "group_id")
    @Cacheable(value = "TIMELINE")
    public void saveTweet(Tweet tweet) {
        hashOperations.put(TIMELINE_CACHE, createKey(tweet), tweet);
    }

    @Cacheable(value = "TIMELINE")
    public Tweet getTweet(String key) {
        return hashOperations.get(TIMELINE_CACHE, key);
    }

//    @KafkaListener(topics = "deleteTweet", groupId = "group_id")
    @Cacheable(value = "TIMELINE")
    public void deleteTweet(UUID id) throws Exception {
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

//    @KafkaListener(topics = "deleteTweet", groupId = "group_id")
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
    public Map<String, Tweet> getAll() {
        return hashOperations.entries(TIMELINE_CACHE);
    }

    @Cacheable(value = "TIMELINE")
    public List<Tweet> createTimelineForUser(String username) {
        List<Tweet> userTimeline = new ArrayList<>();
        List<String> followingList = new ArrayList<>();
//        List<String> followingList = userService.getFollowingList(username);
        if (followingList == null || followingList.size() == 0) {
            return userTimeline;
        }

        for (String followedName : followingList) {
            String keyPattern = followedName + "::" + "*";
            Map<String, Tweet> map = findTweetByPattern(keyPattern);
            userTimeline = new ArrayList<>(map.values());
        }
        Collections.sort(userTimeline);
        Collections.reverse(userTimeline);
        return userTimeline;
    }

}
