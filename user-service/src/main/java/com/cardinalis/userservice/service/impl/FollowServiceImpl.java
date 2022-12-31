package com.cardinalis.userservice.service.impl;

import com.cardinalis.userservice.model.Relationship;
import com.cardinalis.userservice.repository.RelationshipRepository;
import com.cardinalis.userservice.service.FollowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final RelationshipRepository relationshipRepository;

    @Override
    public Relationship follow(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    @Override
    public List<Relationship> getFollowing(UUID userID) {
        log.info("list user's following - {}", userID);
        return relationshipRepository.findByFollowerId(userID);
    }

    @Override
    public List<Relationship> getFollowers(UUID userID) {
        log.info("list user's followers - {}", userID);
        return relationshipRepository.findByFollowedId(userID);
    }

    @Override
    public void unfollow(UUID followedId, UUID followerId) {
        var follow = relationshipRepository.findByFollowerIdAndFollowedId(followedId, followerId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));
        relationshipRepository.delete(follow);
        log.info("user {} unfollowed by {}", followedId, followerId);
    }

}