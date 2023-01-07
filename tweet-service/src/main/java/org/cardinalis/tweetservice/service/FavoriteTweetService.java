package org.cardinalis.tweetservice.service;

import org.cardinalis.tweetservice.model.FavoriteTweet;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface FavoriteTweetService {

    FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet);

    FavoriteTweet deleteFavorite(UUID tweetId, String username);

    FavoriteTweet findFavorite(UUID tweetId, String username);

    public FavoriteTweet findFavoriteById(UUID id);

}
