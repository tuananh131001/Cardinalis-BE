package com.cardinalis.userservice.repository;

import com.cardinalis.userservice.model.Relationship;
import com.cardinalis.userservice.model.RelationshipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, RelationshipId> {

    Optional<Relationship> findByFollowerIdAndFollowedId(UUID followerId, UUID followedId);

    @Query("SELECT r FROM Relationship r WHERE r.followerId = :followerId ORDER BY r.createdAt DESC")
    // query avatar, name, bio of follower users
    List<Relationship> findByFollowerId(UUID followerId);

    @Query("SELECT r FROM Relationship r WHERE r.followedId = :followedId ORDER BY r.createdAt DESC")
    List<Relationship> findByFollowedId(UUID followedId);
}