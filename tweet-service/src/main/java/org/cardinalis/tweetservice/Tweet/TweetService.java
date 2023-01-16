package org.cardinalis.tweetservice.Tweet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.cardinalis.tweetservice.Tweet.Tweet;

import java.util.Map;
public interface TweetService {
    public Tweet saveTweet(Tweet tweet);
    public Tweet editTweet(Tweet newTweet);
    public Tweet getTweetById(Long id);
    public Map<String, Object> getNewestTweetsFromUser(String email, int pageNo, int pageSize) throws JsonProcessingException;
    public Tweet deleteTweet(Long id);
    public Map<String, Object> getAll(int pageNo, int pageSize) throws Exception;

}
