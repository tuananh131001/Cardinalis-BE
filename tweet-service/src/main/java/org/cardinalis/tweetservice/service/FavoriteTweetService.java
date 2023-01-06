package org.cardinalis.tweetservice.service;

import org.cardinalis.tweetservice.model.FavoriteTweet;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface FavoriteTweetService {
    FavoriteTweet favoriteTweetAction(String username, UUID tweeId) ;

    List<FavoriteTweet> listFavoritesByTweet(UUID tweetId) ;

    List<FavoriteTweet> listFavoritesByUser(String username) ;

    void deleteFavoriteByTweetId(UUID tweetId) ;
}
