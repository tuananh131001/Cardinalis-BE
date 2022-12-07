package org.cardinalis.tweetcrd.controller;

import org.cardinalis.tweetcrd.model.Tweet;
import org.cardinalis.tweetcrd.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/tweet")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public void addTweet(@RequestBody Tweet tweet){
        tweetService.saveTweet(tweet);
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
    public void deleteProduct(@PathVariable(value = "id") UUID id){
        tweetService.deleteTweet(id);
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public List<Tweet> findTweetByUserId(@RequestParam String userId) {
        List<Tweet> result = tweetService.getTweetByUserId(userId);
        return result;
    }



}