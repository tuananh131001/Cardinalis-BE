package org.cardinalis.tweetcrd.service;

import org.cardinalis.tweetcrd.model.Tweet;

import java.util.List;
import java.util.UUID;

public interface TweetService {
    public String saveTweet(Tweet tweet);
    public Tweet getTweet(UUID id);
    public List<Tweet> getTweetByUserId(String userId);
    public String deleteTweet(UUID id);

}
