package org.cardinalis.tweetservice.Tweet.service;

import org.cardinalis.tweetservice.Tweet.model.Tweet;

import java.util.Map;
public interface TweetService {
    public Tweet saveTweet(Tweet tweet);
    public Tweet getTweetById(Long id);
    public Map<String, Object> getNewestTweetsFromUser(String username, Boolean needCount, int pageNo, int pageSize);
    public Tweet deleteTweet(Long id);
    public Map<String, Object> getAll(Boolean needCount, int pageNo, int pageSize);

}
