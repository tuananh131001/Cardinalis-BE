package org.cardinalis.tweetservice.service;

import org.cardinalis.tweetservice.model.Tweet;

import java.util.List;
import java.util.UUID;

public interface TweetService {
    public Tweet saveTweet(Tweet tweet);
    public Tweet getTweetById(UUID id);
    public List<Tweet> getNewestTweetsFromUser(String username, int size);
    public Tweet deleteTweet(UUID id);
    public List<Tweet> getAll(int pageNo, int pageSize);

}
