package org.cardinalis.tweetservice.service;

import org.cardinalis.tweetservice.model.Tweet;
import org.cardinalis.tweetservice.model.TweetDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TweetService {
    public Tweet saveTweet(Tweet tweet);
    public Tweet getTweetById(UUID id);
    public Map<String, Object> getNewestTweetsFromUser(String username, int pageNo, int pageSize);
    public Tweet deleteTweet(UUID id);
    public Map<String, Object> getAll(int pageNo, int pageSize);

}
