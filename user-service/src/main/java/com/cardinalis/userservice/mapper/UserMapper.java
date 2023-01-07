package com.cardinalis.userservice.mapper;

import com.cardinalis.userservice.dao.HeaderResponse;
import com.cardinalis.userservice.dao.UserResponse;
import com.cardinalis.userservice.repository.projection.user.UserProjection;
import com.cardinalis.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final BasicMapper basicMapper;
    private final UserService userService;



    public HeaderResponse<UserResponse> getFollowers(Long userId, Pageable pageable) {
        Page<UserProjection> users = userService.getFollowers(userId, pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public HeaderResponse<UserResponse> getFollowing(Long userId, Pageable pageable) {
        Page<UserProjection> users = userService.getFollowing(userId, pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public HeaderResponse<FollowerUserResponse> getFollowerRequests(Pageable pageable) {
        Page<FollowerUserProjection> followers = userService.getFollowerRequests(pageable);
        return basicMapper.getHeaderResponse(followers, FollowerUserResponse.class);
    }

    public NotificationResponse processFollow(Long userId) {
        Map<String, Object> notificationDetails = userService.processFollow(userId);
        Notification notification = (Notification) notificationDetails.get("notification");
        NotificationResponse notificationResponse = basicMapper.convertToResponse(notification, NotificationResponse.class);
        notificationResponse.getUserToFollow().setFollower((Boolean) notificationDetails.get("isFollower"));
        return notificationResponse;
    }

//    public List<UserResponse> overallFollowers(Long userId) {
//        List<BaseUserProjection> users = userService.overallFollowers(userId);
//        return users.stream()
//                .map(baseUserProjection -> {
//                    UserResponse userResponse = basicMapper.convertToResponse(baseUserProjection, UserResponse.class);
//                    Map<String, Object> avatar = baseUserProjection.getAvatar();
//                    userResponse.setAvatar(new ImageResponse((Long) avatar.get("id"), (String) avatar.get("src")));
//                    return userResponse;
//                })
//                .collect(Collectors.toList());
//    }


    public String acceptFollowRequest(Long userId) {
        return userService.acceptFollowRequest(userId);
    }

    public String declineFollowRequest(Long userId) {
        return userService.declineFollowRequest(userId);
    }

    public Boolean processSubscribeToNotifications(Long userId) {
        return userService.processSubscribeToNotifications(userId);
    }


}