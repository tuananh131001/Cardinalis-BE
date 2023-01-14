package org.cardinalis.tweetservice.Tweet;

import org.cardinalis.tweetservice.Tweet.Tweet;

import java.util.Map;
public interface TweetService {
    public Tweet saveTweet(Tweet tweet);
    public Tweet editTweet(Tweet newTweet);
    public Tweet getTweetById(Long id);
    public Map<String, Object> getNewestTweetsFromUser(String usermail, Boolean needCount, int pageNo, int pageSize);
    public Tweet deleteTweet(Long id);
    public Map<String, Object> getAll(Boolean needCount, int pageNo, int pageSize);

}
