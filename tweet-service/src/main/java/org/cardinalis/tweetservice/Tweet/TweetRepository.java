package org.cardinalis.tweetservice.Tweet;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>  {
    Optional<Tweet> findById(Long id);

//    @Query("SELECT t FROM Tweet t WHERE t.email = :email")
    Page<Tweet> findByEmailOrderByCreatedAtDesc(String email, Pageable pageable);
    //findFirstByEmailOrderByCreatedAtDesc
   //select top result
   List<Tweet> findAllByEmail(String email, Pageable pageable);
}
