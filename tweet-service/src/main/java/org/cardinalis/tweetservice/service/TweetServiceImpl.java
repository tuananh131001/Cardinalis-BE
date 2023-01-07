package org.cardinalis.tweetservice.service;

import lombok.AllArgsConstructor;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Transactional
@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService{
    @Autowired
    TweetRepository tweetRepository;

    @Override
    public Tweet saveTweet(Tweet tweet) {
        try {
            return tweetRepository.save(tweet);
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
    public Map<String, Object> getNewestTweetsFromUser(String username, int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
            Page<Tweet> result = tweetRepository.findByUsernameOrderByCreatedAtDesc(username, pageable);
            return createResponse(result.getContent(), result.getNumber(), result.hasNext(), result.getTotalPages(), result.getNumberOfElements(), result.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Tweet deleteTweet(UUID id) {
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
    public Map<String, Object> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
        Page<Tweet> result = tweetRepository.findAll(pageable);
        return createResponse(result.getContent(), result.getNumber(), result.hasNext(), result.getTotalPages(), result.getNumberOfElements(), result.getTotalElements());
    }

    public Map<String, Object> createResponse(Object data, int currentPage, boolean hasNext, int totalPage, long currentPageTotalElement, long totalElement) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("currentPage",currentPage);
        response.put("hasNext",hasNext);
        response.put("totalPage", totalPage);
//        response.put("current_page_total_element", currentPageTotalElement);
//        response.put("total_element", totalElement);
        return response;
    }
}
