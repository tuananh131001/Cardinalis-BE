package org.cardinalis.tweetservice.FavoriteTweet.service;


import org.cardinalis.tweetservice.FavoriteTweet.model.FavoriteTweet;

public interface FavoriteTweetService {

    FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet);

    FavoriteTweet deleteFavorite(Long tweetId, String username);

    FavoriteTweet findFavorite(Long tweetId, String username);

    public FavoriteTweet findFavoriteById(Long id);

}
