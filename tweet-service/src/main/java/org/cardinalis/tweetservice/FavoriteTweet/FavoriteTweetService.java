package org.cardinalis.tweetservice.FavoriteTweet;


import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;

import java.util.List;

public interface FavoriteTweetService {

    FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet);

    FavoriteTweet deleteFavorite(Long tweetId, String username);

    FavoriteTweet findFavorite(Long tweetId, String username);

    public List<FavoriteTweet> findAllFavoritesOfTweet(Long tweetId);

}
