package org.cardinalis.tweetservice.Tweet;

import org.cardinalis.tweetservice.Comment.CommentRepository;
import org.cardinalis.tweetservice.Util.Reusable.*;

import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetRepository;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.cardinalis.tweetservice.Util.Reusable.createPageResponse;
import static org.cardinalis.tweetservice.Util.Reusable.getResultList;

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
    public Map<String, Object> getNewestTweetsFromUser(String email, Boolean needCount, int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
            Page<Tweet> page = tweetRepository.findByEmailOrderByCreatedAtDesc(email, pageable);

            return createPageResponse(getResultList(page.getContent(), needCount), page.getNumber(), page.hasNext(), page.getTotalPages(), page.getNumberOfElements(), page.getSize());
        } catch (Exception e) {
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
    public Map<String, Object> getAll(Boolean needCount, int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
            Page<Tweet> page = tweetRepository.findAll(pageable);
            return  createPageResponse(getResultList(page.getContent(), needCount), page.getNumber(), page.hasNext(), page.getTotalPages(), page.getNumberOfElements(), page.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


//    public TweetDTO setCount(TweetDTO tweetDTO) {
//        tweetDTO.setTotalFav(favoriteTweetRepository.countByTweet_Id(tweetDTO.getId()));
//        tweetDTO.setTotalComment(commentRepository.countByTweet_Id(tweetDTO.getId()));
//        System.out.println(tweetDTO);
//        return tweetDTO;
//    }

}
