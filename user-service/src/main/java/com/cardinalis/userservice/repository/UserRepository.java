package com.cardinalis.userservice.repository;

import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.projection.UserPrincipalProjection;
import com.cardinalis.userservice.repository.projection.user.AuthUserProjection;
import com.cardinalis.userservice.repository.projection.user.FollowerUserProjection;
import com.cardinalis.userservice.repository.projection.user.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Query("SELECT f.id AS id, f.fullName AS fullName, f.username AS username, f.bio AS bio, f.avatar AS avatar " +
            "FROM UserEntity user " +
            "LEFT JOIN user.followers f " +
            "WHERE user.id = :userId")
    Page<UserProjection> getFollowersById(Long userId, Pageable pageable);

    @Query("SELECT f.id AS id, f.fullName AS fullName, f.username AS username, f.bio AS bio, f.avatar AS avatar " +
            "FROM UserEntity user " +
            "LEFT JOIN user.following f " +
            "WHERE user.id = :userId")
    Page<UserProjection> getFollowingById(Long userId, Pageable pageable);

    @Query("SELECT f.id AS id, f.fullName AS fullName, f.username AS username, f.bio AS bio, f.avatar AS avatar " +
            "FROM UserEntity user " +
            "LEFT JOIN user.followerRequests f " +
            "WHERE user.id = :userId")
    Page<FollowerUserProjection> getFollowerRequests(Long userId, Pageable pageable);

//    @Query("SELECT user.userMutedList FROM UserEntity user WHERE user.id = :userId")
//    List<UserEntity> getUserMutedListById(Long userId);



    @Query("SELECT CASE WHEN count(follower) > 0 THEN true ELSE false END " +
            "FROM UserEntity user " +
            "LEFT JOIN user.followers follower " +
            "WHERE user.id = :authUserId " +
            "AND follower.id = :userId")
    boolean isUserFollowByOtherUser(Long authUserId, Long userId);


//    @Query("SELECT CASE WHEN count(subscriber) > 0 THEN true ELSE false END FROM UserEntity user " +
//            "LEFT JOIN user.subscribers subscriber " +
//            "WHERE user.id = :userId " +
//            "AND subscriber.id = :subscriberUserId")
//    boolean isMyProfileSubscribed(Long userId, Long subscriberUserId);
//
//
//    @Query("SELECT user FROM UserEntity user WHERE user.id = :userId")
//    Optional<UserEntity> getUserDetails(Long userId);
//
//    @Query(value = "SELECT users.id as id, users.full_name as fullName, users.username as username, users.about as about, " +
//            "users.private_profile as isPrivateProfile, images.id as img_id, images.src as img_src " +
//            "FROM UserEntity " +
//            "LEFT JOIN user_avatar ON users.id = user_avatar.user_id " +
//            "LEFT JOIN images ON user_avatar.avatar_id = images.id " +
//            "WHERE users.id IN ( " +
//            "SELECT user_subscriptions.subscriber_id FROM UserEntity " +
//            "JOIN user_subscriptions ON users.id = user_subscriptions.user_id " +
//            "WHERE users.id = ?1) " +
//            "INTERSECT " +
//            "SELECT users.id as id, users.full_name as fullName, users.username as username, users.about as about, " +
//            "users.private_profile as isPrivateProfile, images.id as img_id, images.src as img_src " +
//            "FROM UserEntity " +
//            "LEFT JOIN user_avatar ON users.id = user_avatar.user_id " +
//            "LEFT JOIN images ON user_avatar.avatar_id = images.id " +
//            "WHERE users.id IN ( " +
//            "SELECT user_subscriptions.subscriber_id FROM UserEntity " +
//            "JOIN user_subscriptions ON users.id = user_subscriptions.user_id " +
//            "WHERE users.id = ?2)", nativeQuery = true)
//    <T> List<T> getSameFollowers(Long userId, Long authUserId, Class<T> type);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.email = :email WHERE user.id = :userId")
//    void updateEmail(String email, Long userId);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.password = :password WHERE user.id = :userId")
//    void updatePassword(String password, Long userId);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.passwordResetCode = :passwordResetCode WHERE user.id = :userId")
//    void updatePasswordResetCode(String passwordResetCode, Long userId);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.active = true WHERE user.id = :userId")
//    void updateActiveUserProfile(Long userId);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.profileStarted = true WHERE user.id = :userId")
//    void updateProfileStarted(Long userId);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.activationCode = :activationCode WHERE user.id = :userId")
//    void updateActivationCode(String activationCode, Long userId);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.username = :username WHERE user.id = :userId")
//    void updateUsername(String username, Long userId);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.countryCode = :countryCode, user.phone = :phone WHERE user.id = :userId")
//    void updatePhone(String countryCode, Long phone, Long userId);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.country = :country WHERE user.id = :userId")
//    void updateCountry(String country, Long userId);
//
//    @Modifying
//    @Query("UPDATE UserEntity user SET user.gender = :gender WHERE user.id = :userId")
//    void updateGender(String gender, Long userId);

    @Query("SELECT user FROM UserEntity user WHERE user.username = :username")
    Optional<AuthUserProjection> findAuthUserByUsername(String username);

}