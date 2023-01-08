package org.cardinalis.tweetservice.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByUsernameAndTweet_Id(String username, Long tweet_id);

//    @Query("SELECT c FROM Comment c WHERE c.id = :tweet_id")
    Page<Comment> findByTweet_Id(@Param("tweet_id") Long tweetId, Pageable pageable);

    long countByTweet_Id(Long tweetId);

}
