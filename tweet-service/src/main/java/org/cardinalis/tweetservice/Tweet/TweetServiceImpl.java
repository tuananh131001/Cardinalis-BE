package org.cardinalis.tweetservice.Tweet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.cardinalis.tweetservice.Comment.CommentRepository;
import org.cardinalis.tweetservice.DTO.TweetAuthorDTO;

import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetRepository;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.cardinalis.tweetservice.Util.Reusable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.cardinalis.tweetservice.Util.Reusable.*;

@Transactional
@Service
@EnableKafka
public class TweetServiceImpl implements TweetService {
    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FavoriteTweetRepository favoriteTweetRepository;

    @Autowired
    TweetDTOService tweetDTOService;

    @Autowired
    Reusable reusable;


    @Override
    public Tweet saveTweet(Tweet tweet) {
        try {
            return tweetRepository.save(tweet);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Tweet editTweet(Tweet newTweet) {
        try {
            Tweet oldTweet = getTweetById(newTweet.getId());
            oldTweet.setContent(newTweet.getContent());
            oldTweet.setLastEdit(LocalDateTime.now());
            return tweetRepository.save(oldTweet);
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public Tweet getTweetById(Long id) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new NoContentFoundException("Tweet not found for id = " + id));
        return tweet;
    }

    @Override
    public Map<String, Object> getNewestTweetsFromUser(String email, int pageNo, int pageSize) throws JsonProcessingException {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
            Page<Tweet> page = tweetRepository.findByEmailOrderByCreatedAtDesc(email, pageable);
            TweetAuthorDTO authorDTO = reusable.getUserInfo(email);
            List<TweetDTO> tweetDTOS = page.getContent().stream().map(tweet -> new TweetDTO(tweet,authorDTO)).collect(Collectors.toList());
            return createPageResponse(tweetDTOS, page.getNumber(), page.hasNext(), page.getTotalPages(), page.getNumberOfElements(), page.getSize());
        } catch (JsonProcessingException e) {
            System.out.println("huhu json processing");
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Tweet deleteTweet(Long id) {
        try {
            Tweet tweet = getTweetById(id);
            tweetRepository.delete(tweet);
            return tweet;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Map<String, Object> getAll(int pageNo, int pageSize) throws Exception {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
            Page<Tweet> page = tweetRepository.findAll(pageable);
            return  createPageResponse(reusable.getTweetDTOList(page.getContent()), page.getNumber(), page.hasNext(), page.getTotalPages(), page.getNumberOfElements(), page.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}
