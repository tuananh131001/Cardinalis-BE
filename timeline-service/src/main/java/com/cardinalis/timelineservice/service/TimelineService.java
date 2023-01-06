package com.cardinalis.timelineservice.service;

import com.cardinalis.timelineservice.model.Timeline;
import com.cardinalis.timelineservice.repository.TimelineRepository;
import com.cardinalis.userservice.service.UserService;
import org.cardinalis.tweetservice.model.Tweet;
import org.cardinalis.tweetservice.service.TweetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//@Transactional
@Repository
@Service
public class
TimelineService implements TimelineRepository{
    private final String TIMELINE_CACHE = "TIMELINE";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Timeline> hashOperations;

    @Autowired
    UserService userService;

    @Autowired
    TweetServiceImpl tweetService;

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    @Cacheable(value = "TIMELINE")
    public void saveTimeline(Timeline timeline) {
        hashOperations.put(TIMELINE_CACHE, timeline.getUsername(), timeline);
    }

    @Override
    @Cacheable(value = "TIMELINE")
    public Timeline updateTimeline(String username) {
        try {
            Timeline timeline = getTimeline(username);
            if (timeline == null) {
                timeline.setUsername(username);
            }
            List<Tweet> userTimeline = null;
            List<String> followingList = userService.getFollowingList(username);
            if (followingList == null || followingList.size() == 0) {
                userTimeline.addAll(tweetService.getAll(0, 20));
            } else {
                for (String user : followingList) {
                    userTimeline.addAll(tweetService.getNewestTweetsFromUser(user, 0, 20));
                }
                Collections.sort(userTimeline);
                Collections.reverse(userTimeline);
            }
            timeline.setUserTimeline(userTimeline);
            saveTimeline(timeline);
            return timeline;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Cacheable(value = "TIMELINE")
    public Timeline getTimeline(String username) {
        return hashOperations.get(TIMELINE_CACHE, username);
    }

    @Override
    @Cacheable(value = "TIMELINE")
    public void deleteTimeline(String username) {
        hashOperations.delete(TIMELINE_CACHE, username);
    }

    @Override
    @Cacheable(value = "TIMELINE")
    public Map<String, Timeline> getAll() {
        return hashOperations.entries(TIMELINE_CACHE);
    }

}
