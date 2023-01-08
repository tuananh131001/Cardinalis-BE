package org.cardinalis.tweetservice.FavoriteTweet;


import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;

public interface FavoriteTweetService {

    FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet);

    FavoriteTweet deleteFavorite(Long tweetId, String username);

    FavoriteTweet findFavorite(Long tweetId, String username);

    public FavoriteTweet findFavoriteById(Long id);

}
