package org.cardinalis.tweetservice.controller;

import org.cardinalis.tweetservice.model.Tweet;
import org.cardinalis.tweetservice.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public String addTweet(@RequestBody Tweet tweet){
        if (tweet.getCreatedAt() == null) tweet.setCreatedAt(LocalDateTime.now());
        return tweetService.saveTweet(tweet);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Tweet> getTweet(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize)
    {return tweetService.getTweet(pageNo, pageSize);}


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Tweet getTweetById(@PathVariable(value = "id") UUID id){
        return tweetService.getTweetById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public String deleteTweet(@PathVariable(value = "id") UUID id){
        return tweetService.deleteTweet(id);
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public List<Tweet> findTweetByUsername(@RequestParam String username) {
        List<Tweet> result = tweetService.getTweetByUsername(username);
        return result;
    }



}
