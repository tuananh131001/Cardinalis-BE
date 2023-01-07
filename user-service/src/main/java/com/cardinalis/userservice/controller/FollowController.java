package com.cardinalis.userservice.controller;

import com.cardinalis.userservice.dao.FailResponseDTO;
import com.cardinalis.userservice.dao.FollowDTO;
import com.cardinalis.userservice.dao.SuccessResponseDTO;
import com.cardinalis.userservice.model.Relationship;
import com.cardinalis.userservice.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/user/follow")
@AllArgsConstructor
public class FollowController {


    private final FollowService followService;

    @GetMapping("/{userid}/following")
    public ResponseEntity<Object> getFollowing(@PathVariable(name = "userid") UUID userId) {
        try {
            var follows = followService.getFollowing(userId);
            return ResponseEntity.ok(SuccessResponseDTO.builder()
                    .data(follows)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(FailResponseDTO.builder()
                    .errors_message(e.getMessage())
                    .code("404")
                    .build());
        }
    }

    @GetMapping("/{userid}/followers")
    public ResponseEntity<Object> getFollowers(@PathVariable(name = "userid") UUID userId) {
        try {
            var followers = followService.getFollowers(userId);
            return ResponseEntity.ok(SuccessResponseDTO.builder()
                    .data(followers)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(FailResponseDTO.builder()
                    .errors_message(e.getMessage())
                    .code("404")
                    .build());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createRelationship(@RequestBody FollowDTO followDataContract) {
        try {
            Relationship relationship = Relationship.builder()
                    .followedId(followDataContract.getFollowedId())
                    .followerId(followDataContract.getFollowerId())
                    .createdAt(LocalDateTime.now())
                    .build();
            // check duplicate

            var followed = followService.follow(relationship);
            return ResponseEntity.created(URI.create("")).body(SuccessResponseDTO.builder()
                    .data(followed)
                    .success(true)
                    .code("200")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(FailResponseDTO.builder()
                    .errors_message(e.getMessage())
                    .code("404")
                    .build());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteRelationship(@RequestBody FollowDTO followDataContract) {
        try {
            followService.unfollow(followDataContract.getFollowedId(), followDataContract.getFollowerId());
            return ResponseEntity.ok(SuccessResponseDTO.builder()
                    .data(null)
                    .success(true)
                    .code("200")
                    .errors_message("Follow deleted")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(FailResponseDTO.builder()
                    .errors_message("No user founded")
                    .code("404")
                    .build());

        }
    }
}