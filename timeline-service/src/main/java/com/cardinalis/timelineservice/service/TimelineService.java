package com.cardinalis.timelineservice.service;

import com.cardinalis.timelineservice.model.Timeline;
import com.cardinalis.timelineservice.repository.TimelineRepository;
import com.cardinalis.userservice.service.UserService;
import org.cardinalis.tweetservice.model.Tweet;
import org.cardinalis.tweetservice.service.TweetSeriveImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

//@Transactional
@Service
@Import(value = {UserService.class, TweetSeriveImpl.class})
public class TimelineService implements TimelineRepository{
    private final String TIMELINE_CACHE = "TIMELINE";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Timeline> hashOperations;

    @Autowired
    UserService userService;

    @Autowired
    TweetSeriveImpl tweetService;

    // This annotation makes sure that the method needs to be executed after
    // dependency injection is done to perform any initialization.
    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveTimeline(Timeline timeline) {
        hashOperations.put(TIMELINE_CACHE, timeline.getUsername(), timeline);
    }

    @Override
    public void updateTimeline(String username) {
        Timeline timeline = getTimelineByUsername(username);
        if (timeline == null) {timeline.setUsername(username);}
        List<Tweet> userTimeline = null;
        List<String> followingList = userService.getFollowingList(username);
        for (String user: followingList) {
            userTimeline.addAll(tweetService.getTweetByUsername(user));
        }
        Collections.sort(userTimeline);
        Collections.reverse(userTimeline);
        timeline.setUserTimeline(userTimeline);
        saveTimeline(timeline);
    }

    public Timeline getTimelineByUsername(String username) {
        return (Timeline) hashOperations.get(TIMELINE_CACHE, username);
    }

    @Override
    public void deleteTimeline(String username) {
        hashOperations.delete(TIMELINE_CACHE, username);
    }

}
