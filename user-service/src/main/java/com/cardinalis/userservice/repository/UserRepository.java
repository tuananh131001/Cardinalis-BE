package com.cardinalis.userservice.repository;

import com.cardinalis.userservice.dao.UserEntityDTO;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.projection.user.FollowerUserProjection;
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
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findById(UUID id);

    // sort by created_at DESC findById


    Optional<UserEntity> findByUsername(String username);


    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.username LIKE %:username%")
    List<UserEntity> findByUsernameContaining(String username);

//    Followers & following
    @Query("SELECT CASE WHEN count(user) > 0 THEN true ELSE false END FROM User user WHERE user.id = :userId")
    boolean isUserExist(Long userId);

    @Query("SELECT follower.id FROM User user LEFT JOIN user.followers follower WHERE user.id = :userId")
    List<Long> getUserFollowersIds(Long userId);

    @Query("SELECT f.id AS id, f.fullName AS fullName, f.username AS username, f.about AS about, f.avatar AS avatar, " +
            "f.privateProfile AS privateProfile, f.mutedDirectMessages AS mutedDirectMessages " +
            "FROM User user " +
            "LEFT JOIN user.followers f " +
            "WHERE user.id = :userId")
    Page<UserEntityDTO> getFollowersById(Long userId, Pageable pageable);

    @Query("SELECT f.id AS id, f.fullName AS fullName, f.username AS username, f.about AS about, f.avatar AS avatar, " +
            "f.privateProfile AS privateProfile, f.mutedDirectMessages AS mutedDirectMessages " +
            "FROM User user " +
            "LEFT JOIN user.following f " +
            "WHERE user.id = :userId")
    Page<UserEntityDTO> getFollowingById(Long userId, Pageable pageable);

    @Query("SELECT f.id AS id, f.fullName AS fullName, f.username AS username, f.about AS about, f.avatar AS avatar " +
            "FROM User user " +
            "LEFT JOIN user.followerRequests f " +
            "WHERE user.id = :userId")
    Page<FollowerUserProjection> getFollowerRequests(Long userId, Pageable pageable);

    @Query("SELECT user.userMutedList FROM User user WHERE user.id = :userId")
    List<UserEntity> getUserMutedListById(Long userId);



    @Query("SELECT CASE WHEN count(follower) > 0 THEN true ELSE false END " +
            "FROM User user " +
            "LEFT JOIN user.followers follower " +
            "WHERE user.id = :authUserId " +
            "AND follower.id = :userId")
    boolean isUserFollowByOtherUser(Long authUserId, Long userId);

    @Query("SELECT CASE WHEN count(followerRequest) > 0 THEN true ELSE false END FROM User user " +
            "LEFT JOIN user.followerRequests followerRequest " +
            "WHERE user.id = :userId " +
            "AND followerRequest.id = :authUserId")
    boolean isMyProfileWaitingForApprove(Long userId, Long authUserId);

    @Query("SELECT CASE WHEN count(subscriber) > 0 THEN true ELSE false END FROM User user " +
            "LEFT JOIN user.subscribers subscriber " +
            "WHERE user.id = :userId " +
            "AND subscriber.id = :subscriberUserId")
    boolean isMyProfileSubscribed(Long userId, Long subscriberUserId);

    @Query("SELECT CASE WHEN count(participantUser) > 0 THEN true ELSE false END FROM User user " +
            "LEFT JOIN user.chats chats " +
            "LEFT JOIN chats.chat chat " +
            "LEFT JOIN chat.participants participant " +
            "LEFT JOIN participant.user participantUser " +
            "WHERE user.id = :authUserId " +
            "AND participantUser.id = :userId")
    boolean isUserChatParticipant(Long userId, Long authUserId);

    @Query("SELECT user FROM User user WHERE user.id = :userId")
    Optional<UserEntity> getUserDetails(Long userId);

    @Query(value = "SELECT users.id as id, users.full_name as fullName, users.username as username, users.about as about, " +
            "users.private_profile as isPrivateProfile, images.id as img_id, images.src as img_src " +
            "FROM users " +
            "LEFT JOIN user_avatar ON users.id = user_avatar.user_id " +
            "LEFT JOIN images ON user_avatar.avatar_id = images.id " +
            "WHERE users.id IN ( " +
            "SELECT user_subscriptions.subscriber_id FROM users " +
            "JOIN user_subscriptions ON users.id = user_subscriptions.user_id " +
            "WHERE users.id = ?1) " +
            "INTERSECT " +
            "SELECT users.id as id, users.full_name as fullName, users.username as username, users.about as about, " +
            "users.private_profile as isPrivateProfile, images.id as img_id, images.src as img_src " +
            "FROM users " +
            "LEFT JOIN user_avatar ON users.id = user_avatar.user_id " +
            "LEFT JOIN images ON user_avatar.avatar_id = images.id " +
            "WHERE users.id IN ( " +
            "SELECT user_subscriptions.subscriber_id FROM users " +
            "JOIN user_subscriptions ON users.id = user_subscriptions.user_id " +
            "WHERE users.id = ?2)", nativeQuery = true)
    <T> List<T> getSameFollowers(Long userId, Long authUserId, Class<T> type);

    @Modifying
    @Query("UPDATE User user SET user.email = :email WHERE user.id = :userId")
    void updateEmail(String email, Long userId);

    @Modifying
    @Query("UPDATE User user SET user.password = :password WHERE user.id = :userId")
    void updatePassword(String password, Long userId);

    @Modifying
    @Query("UPDATE User user SET user.passwordResetCode = :passwordResetCode WHERE user.id = :userId")
    void updatePasswordResetCode(String passwordResetCode, Long userId);

    @Modifying
    @Query("UPDATE User user SET user.active = true WHERE user.id = :userId")
    void updateActiveUserProfile(Long userId);

    @Modifying
    @Query("UPDATE User user SET user.profileStarted = true WHERE user.id = :userId")
    void updateProfileStarted(Long userId);

    @Modifying
    @Query("UPDATE User user SET user.activationCode = :activationCode WHERE user.id = :userId")
    void updateActivationCode(String activationCode, Long userId);

    @Modifying
    @Query("UPDATE User user SET user.username = :username WHERE user.id = :userId")
    void updateUsername(String username, Long userId);

    @Modifying
    @Query("UPDATE User user SET user.countryCode = :countryCode, user.phone = :phone WHERE user.id = :userId")
    void updatePhone(String countryCode, Long phone, Long userId);

    @Modifying
    @Query("UPDATE User user SET user.country = :country WHERE user.id = :userId")
    void updateCountry(String country, Long userId);

    @Modifying
    @Query("UPDATE User user SET user.gender = :gender WHERE user.id = :userId")
    void updateGender(String gender, Long userId);

    @Modifying
    @Query("UPDATE User user SET user.language = :language WHERE user.id = :userId")
    void updateLanguage(String language, Long userId);

}