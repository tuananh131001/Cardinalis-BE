package com.cardinalis.timelineservice.repository;

import com.cardinalis.timelineservice.model.Timeline;

import java.util.Map;

//@Repository
//public interface TimelineRepository extends JpaRepository<Timeline, String> {
//}

public interface TimelineRepository {
    void saveTimeline(Timeline timeline);

    void updateTimeline(String username);

    Timeline getTimeline(String username);

    void deleteTimeline(String username);

    Map<String, Timeline> getAll();
}

