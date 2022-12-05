package org.cardinalis.tweetcrd.service;

import org.cardinalis.tweetcrd.model.Tweet;
import org.cardinalis.tweetcrd.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class TweetSeriveImpl implements TweetService{
    @Autowired
    TweetRepository tweetRepository;

    @Override
    public String saveTweet(Tweet tweet) {
        try {
            tweetRepository.save(tweet);
            return "saved tweet";
        } catch (Exception e) {
            e.printStackTrace();
            return "failed to save tweet";
        }
    }

    @Override
    public Tweet getTweetById(UUID id) {
        Tweet tweet = null;
        try {
            tweet = tweetRepository.findById(id)
                    .orElseThrow(() -> new Exception("Tweet not found for id: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tweet;
    }

    @Override
    public List<Tweet> getTweetByUserId(String userId) {
        List<Tweet> tweets = null;
        try {
            tweets = tweetRepository.findByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tweets;
    }

    @Override
    public String deleteTweet(UUID id) {
        try {
            Tweet tweet = getTweetById(id);
            tweetRepository.delete(tweet);
            return "deleted tweet";
        } catch (Exception e) {
            return "failed to delete tweet";
        }
    }

    @Override
    public List<Tweet> getTweet(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Tweet> result = tweetRepository.findAll(pageable);
        return result.getContent();
    }
}
