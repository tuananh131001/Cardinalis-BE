package com.cardinalis.userservice.service.impl;

import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.dao.UserShortInfoDTO;
import com.cardinalis.userservice.model.Relationship;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.RelationshipRepository;
import com.cardinalis.userservice.repository.UserRepository;
import com.cardinalis.userservice.service.FollowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final RelationshipRepository relationshipRepository;
    private final UserRepository userRepository;

    private final ModelMapper mapper;
    @Override
    public Relationship follow(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    @Override
    public List<UserEntityDTO> getFollowing(UUID userID) {
        log.info("list user's following - {}", userID);
        List<UserEntityDTO> userEntities = new ArrayList<>();
        List<Relationship> relationships = relationshipRepository.findByFollowerId(userID);
        for (Relationship relationship : relationships) {
            UserEntity user = userRepository.findById(relationship.getFollowerId()).get();
            UserEntityDTO userDTO = mapper.map(user, UserEntityDTO.class);
            userEntities.add(userDTO);
        }
        return userEntities;
    }

    @Override
    public List<UserEntityDTO> getFollowers(UUID userID) {
        log.info("list user's followers - {}", userID);
        List<UserEntityDTO> userEntities = new ArrayList<>();
        List<Relationship> relationships = relationshipRepository.findByFollowedId(userID);
        // get user's followers info
        for (Relationship relationship : relationships) {
            UserEntity user = userRepository.findById(relationship.getFollowerId()).get();
            UserEntityDTO userDTO = mapper.map(user, UserEntityDTO.class);
            userEntities.add(userDTO);
        }
        return userEntities;
    }

    @Override
    public void unfollow(UUID followedId, UUID followerId) {
        var follow = relationshipRepository.findByFollowerIdAndFollowedId(followedId, followerId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));
        relationshipRepository.delete(follow);
        log.info("user {} unfollowed by {}", followedId, followerId);
    }

}