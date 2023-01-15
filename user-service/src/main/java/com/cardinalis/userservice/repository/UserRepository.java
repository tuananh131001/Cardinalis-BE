package com.cardinalis.userservice.repository;

import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.projection.UserPrincipalProjection;
import com.cardinalis.userservice.repository.projection.user.AuthUserProjection;
import com.cardinalis.userservice.repository.projection.user.FollowerUserProjection;
import com.cardinalis.userservice.repository.projection.user.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // sort by created_at DESC findById
    @Query("SELECT new com.cardinalis.userservice.repository.projection.UserPrincipalProjection(user.id, user.email, user.password, user.activationCode) FROM UserEntity user WHERE user.email = :email")
    Optional<UserPrincipalProjection> findUserPrincipalByEmail(String email);

    Optional<UserEntity> findByUsername(String username);


    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.username LIKE %:username%")
    List<UserEntity> findByUsernameContaining(String username);

//    Followers & following
    @Query("SELECT CASE WHEN count(user) > 0 THEN true ELSE false END FROM UserEntity user WHERE user.id = :userId")
    boolean isUserExist(Long userId);

//    @Query("SELECT follower.id FROM UserEntity user LEFT JOIN user.followers follower WHERE user.id = :userId")
//    List<Long> getUserFollowersIds(Long userId);

    @Query("SELECT f.id AS id,f.email AS email , f.fullName AS fullName, f.username AS username, f.bio AS bio, f.avatar AS avatar FROM UserEntity u JOIN u.followers f WHERE u.id = :userId")
    Page<UserProjection> getFollowersById(Long userId, Pageable pageable);

    @Query("SELECT f.id AS id,f.email AS email , f.fullName AS fullName, f.username AS username, f.bio AS bio, f.avatar AS avatar FROM UserEntity u JOIN u.following f WHERE u.id = :userId")
    Page<UserProjection> getFollowingById(Long userId, Pageable pageable);



//    @Query("SELECT user.userMutedList FROM UserEntity user WHERE user.id = :userId")
//    List<UserEntity> getUserMutedListById(Long userId);


    // explain:
    @Query("SELECT CASE WHEN count(follower) > 0 THEN true ELSE false END " +
            "FROM UserEntity user " +
            "LEFT JOIN user.followers follower " +
            "WHERE user.id = :authUserId " +
            "AND follower.id = :userId")
    boolean isUserFollowByOtherUser(Long authUserId, Long userId);

    // check if I follow this user
    @Query("SELECT CASE WHEN count(following) > 0 THEN true ELSE false END " +
            "FROM UserEntity user " +
            "LEFT JOIN user.following following " +
            "WHERE user.id = :authUserId " +
            "AND following.id = :userId")
    boolean isFollowingOneWay(Long authUserId,Long userId);
    @Query("SELECT users FROM UserEntity users WHERE users.email = :email")
    Optional<UserEntity> findAuthUserByEmail(String email);

}