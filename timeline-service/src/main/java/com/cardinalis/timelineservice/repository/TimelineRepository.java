package com.cardinalis.timelineservice.repository;

import com.cardinalis.timelineservice.model.Timeline;
import org.cardinalis.tweetservice.Tweet.Tweet;

import java.util.Map;

//@Repository
//public interface TimelineRepository extends JpaRepository<Timeline, String> {
//}

public interface TimelineRepository {
    void saveTweet(Tweet tweet);
    void saveTimeline(Timeline timeline);

    Timeline updateTimeline(String username);

    Timeline getTimeline(String username);

    void deleteTimeline(String username);

    Map<String, Timeline> getAll();
}

