package com.cardinalis.timelineservice.repository;

import com.cardinalis.timelineservice.model.Timeline;

//@Repository
//public interface TimelineRepository extends JpaRepository<Timeline, String> {
//}

public interface TimelineRepository {
    void saveTimeline(Timeline timeline);

    void updateTimeline(String username);

    Timeline getTimelineByUsername(String username);

    void deleteTimeline(String username);
}

