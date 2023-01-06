package org.cardinalis.tweetservice.repository;

import org.cardinalis.tweetservice.model.FavoriteTweet;
import org.cardinalis.tweetservice.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FavoriteTweetRepository extends JpaRepository<FavoriteTweet, UUID> {

    List<FavoriteTweet> findAllByTweetId(UUID tweetId);

    List<FavoriteTweet> findAllByUsername(String username);

    FavoriteTweet findFavoriteTweetByTweetIdAndAndUsername(UUID tweetId, String username);


}
