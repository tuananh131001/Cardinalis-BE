package org.cardinalis.tweetservice.service;

import lombok.extern.slf4j.Slf4j;
import org.cardinalis.tweetservice.model.FavoriteTweet;
import org.cardinalis.tweetservice.repository.FavoriteTweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FavoriteTweetServiceImpl implements FavoriteTweetService {

    @Autowired
    FavoriteTweetRepository favoriteTweetRepository;

    @Override
    public FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet) {
        return favoriteTweetRepository.save(favoriteTweet);
    }

    @Override
    public FavoriteTweet deleteFavorite(UUID tweeId, String username) {
        FavoriteTweet found = findFavorite(tweeId, username);
        favoriteTweetRepository.delete(found);
        return found;
    }

    @Override
    public List<FavoriteTweet> listFavoritesByTweet(UUID tweeId) {
        try {
            return favoriteTweetRepository.findAllByTweetId(tweeId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<FavoriteTweet> listFavoritesByUser(String username) {
        try {
            return favoriteTweetRepository.findAllByUsername(username);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public FavoriteTweet findFavorite(UUID tweetId, String username) {
        return favoriteTweetRepository.findByTweetIdAndUsername(tweetId, username);
    }

    @Override
    public void deleteFavoritesByTweetId(UUID tweetId) {
        try {
            List<FavoriteTweet> favoriteTweets = listFavoritesByTweet(tweetId);
            favoriteTweetRepository.deleteAll(favoriteTweets);
        } catch (Exception e) {
            throw e;
        }
    }

}
