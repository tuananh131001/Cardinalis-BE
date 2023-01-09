package org.cardinalis.tweetservice.FavoriteTweet;


import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;

import java.util.List;

public interface FavoriteTweetService {

    FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet);

    FavoriteTweet deleteFavorite(Long tweetId, String usermail);

    FavoriteTweet findFavorite(Long tweetId, String usermail);

    public List<FavoriteTweet> findAllFavoritesOfTweet(Long tweetId);

}
