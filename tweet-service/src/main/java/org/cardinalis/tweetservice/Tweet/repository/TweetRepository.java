package org.cardinalis.tweetservice.Tweet.repository;
import org.cardinalis.tweetservice.Tweet.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(Long id);

//    @Query("SELECT t FROM Tweet t WHERE t.username = :username")
    Page<Tweet> findByUsernameOrderByCreatedAtDesc(String username, Pageable pageable);

}
