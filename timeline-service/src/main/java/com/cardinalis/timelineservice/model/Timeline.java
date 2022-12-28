package com.cardinalis.timelineservice.model;

import lombok.Data;
import org.cardinalis.tweetservice.model.Tweet;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@RedisHash("Timeline")
public class Timeline implements Serializable {
    @Id
    private String username;

    private List<Tweet> userTimeline;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserTimeline(List<Tweet> userTimeline) {
        this.userTimeline = userTimeline;
    }

    public String getUsername() {
        return username;
    }

    public List<Tweet> getUserTimeline() {
        return userTimeline;
    }
}