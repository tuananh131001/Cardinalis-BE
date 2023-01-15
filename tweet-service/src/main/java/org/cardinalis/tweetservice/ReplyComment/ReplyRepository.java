package org.cardinalis.tweetservice.ReplyComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByEmailAndComment_Id(String email, Long commentId);

//    @Query("SELECT c FROM ReplyComment c WHERE c.id = :tweet_id")
    Page<Reply> findByComment_Id(Long commentId, Pageable pageable);

    long countByComment_Id(Long commentId);

}
