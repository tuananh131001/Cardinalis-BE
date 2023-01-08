package org.cardinalis.tweetservice.FavoriteTweet.repository;

import org.cardinalis.tweetservice.FavoriteTweet.model.FavoriteTweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteTweetRepository extends JpaRepository<FavoriteTweet, Long> {

    Optional<FavoriteTweet> findByUsernameAndTweet_Id(String username, Long tweetId);

    long countByTweet_Id(Long tweetId);

}
