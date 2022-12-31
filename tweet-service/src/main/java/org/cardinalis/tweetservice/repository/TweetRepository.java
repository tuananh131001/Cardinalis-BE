package org.cardinalis.tweetservice.repository;
import org.cardinalis.tweetservice.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, UUID> {
    Optional<Tweet> findById(UUID id);

    @Query("SELECT t FROM Tweet t WHERE t.username = :username")
    Page<Tweet> findByUsernameOrderByCreatedAtDesc(@Param("username") String username, Pageable pageable);

}
