package org.cardinalis.tweetservice.FavoriteTweet;


import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

@EnableKafka
public interface FavoriteTweetService {

    @KafkaListener(topics = "saveFav", groupId = "group_id")
    FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet);

    @KafkaListener(topics = "deleteFav", groupId = "group_id")
    FavoriteTweet deleteFavorite(Long tweetId, String email);

    FavoriteTweet findFavorite(Long tweetId, String email);

    public List<FavoriteTweet> findAllFavoritesOfTweet(Long tweetId);

}
