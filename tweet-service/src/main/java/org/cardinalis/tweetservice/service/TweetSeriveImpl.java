package org.cardinalis.tweetservice.service;

import org.cardinalis.tweetservice.exception.NoContentFoundException;
import org.cardinalis.tweetservice.model.Tweet;
import org.cardinalis.tweetservice.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class TweetSeriveImpl implements TweetService{
    @Autowired
    TweetRepository tweetRepository;

    @Override
    public Tweet saveTweet(Tweet tweet) {
        try {
            tweetRepository.save(tweet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tweet;

    }

    @Override
    public Tweet getTweetById(UUID id) {
        return tweetRepository.findById(id)
                    .orElseThrow(() -> new NoContentFoundException("Tweet not found for id = " + id));
    }

    @Override
    public List<Tweet> getNewestTweetsFromUser(String username, int size) {
        List<Tweet> result = new ArrayList<>();
        try {
            Pageable pageable = PageRequest.of(0, size, Sort.Direction.DESC,"createdAt");
            Page<Tweet> page = tweetRepository.findByUsernameOrderByCreatedAtDesc(username, pageable);
            result = page.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Tweet deleteTweet(UUID id) {
        try {
            Tweet tweet = getTweetById(id);
            tweetRepository.delete(tweet);
            return tweet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Tweet> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
        Page<Tweet> result = tweetRepository.findAll(pageable);
        return result.getContent();
    }
}
