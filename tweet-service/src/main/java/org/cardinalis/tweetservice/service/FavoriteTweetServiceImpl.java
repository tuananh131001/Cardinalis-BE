package org.cardinalis.tweetservice.service;

import lombok.extern.slf4j.Slf4j;
import org.cardinalis.tweetservice.exception.NoContentFoundException;
import org.cardinalis.tweetservice.model.FavoriteTweet;
import org.cardinalis.tweetservice.repository.FavoriteTweetRepository;
import org.cardinalis.tweetservice.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FavoriteTweetServiceImpl implements FavoriteTweetService {

    @Autowired
    FavoriteTweetRepository favoriteTweetRepository;

    @Autowired
    TweetService tweetService;

    @Autowired
    TweetRepository tweetRepository;

    @Override
    public FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet) {
        try {
            FavoriteTweet find = findFavorite(favoriteTweet.getTweet().getId(), favoriteTweet.getUsername());
            if (find == null) return favoriteTweetRepository.save(favoriteTweet);
            throw new IllegalArgumentException("fav already exists");
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public FavoriteTweet deleteFavorite(UUID tweetId, String username) {
        FavoriteTweet found = findFavorite(tweetId, username);
        if (found == null) throw new NoContentFoundException("this fav not exits");
        else {
            favoriteTweetRepository.delete(found);
            return found;
        }
    }

    @Override
    public FavoriteTweet findFavorite(UUID tweetId, String username) {
        try {
            FavoriteTweet result = favoriteTweetRepository.findByUsernameAndTweetuuid(username, tweetId.toString());
            return result;

        } catch (NoContentFoundException e) {
            e.printStackTrace();
            throw new NoContentFoundException("not found tweet with this id");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public FavoriteTweet findFavoriteById(UUID id) {
        return favoriteTweetRepository.findById(id)
                .orElseThrow(() -> new NoContentFoundException("no fav found"));
    }

//    @Override
//    public void deleteFavoritesByTweetId(UUID tweetId) {
//        try {
//            List<FavoriteTweet> favoriteTweets = listFavoritesByTweet(tweetId);
//            favoriteTweetRepository.deleteAll(favoriteTweets);
//        } catch (Exception e) {
//            throw e;
//        }
//    }

}
