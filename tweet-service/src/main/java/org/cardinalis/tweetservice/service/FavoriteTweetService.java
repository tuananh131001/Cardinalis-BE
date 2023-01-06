package org.cardinalis.tweetservice.service;

import org.cardinalis.tweetservice.model.FavoriteTweet;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface FavoriteTweetService {

    FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet);

    FavoriteTweet deleteFavorite(UUID tweeId, String username);

    List<FavoriteTweet> listFavoritesByTweet(UUID tweetId) ;

    List<FavoriteTweet> listFavoritesByUser(String username) ;

    FavoriteTweet findFavorite(UUID tweetId, String username);

    void deleteFavoritesByTweetId(UUID tweetId) ;


}
