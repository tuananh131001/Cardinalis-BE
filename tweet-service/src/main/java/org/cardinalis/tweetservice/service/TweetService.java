package org.cardinalis.tweetservice.service;

import org.cardinalis.tweetservice.model.Tweet;
import org.cardinalis.tweetservice.model.TweetDTO;

import java.util.List;
import java.util.UUID;

public interface TweetService {
    public void saveTweet(Tweet tweet);
    public Tweet getTweetById(UUID id);
    public List<Tweet> getNewestTweetsFromUser(String username, int pageNo, int pageSize);
    public Tweet deleteTweet(UUID id);
    public List<Tweet> getAll(int pageNo, int pageSize);

}
