package org.cardinalis.tweetcrd.repository;
import org.cardinalis.tweetcrd.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(UUID id);

    @Query("SELECT t FROM Tweet t WHERE t.userId = :userId")
    List<Tweet> findByUserId(@Param("author") String userId);

}
