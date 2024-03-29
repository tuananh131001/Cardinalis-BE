package org.cardinalis.tweetservice.FavoriteTweet;

import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteTweetRepository extends JpaRepository<FavoriteTweet, Long> {
    List<FavoriteTweet> findByTweet_Id(Long tweetId);

    Optional<FavoriteTweet> findByEmailAndTweet_Id(String email, Long tweetId);

    long countByTweet_Id(Long tweetId);

    Page<FavoriteTweet> findByEmailOrderByCreatedAt(String email, Pageable pageable);
}
