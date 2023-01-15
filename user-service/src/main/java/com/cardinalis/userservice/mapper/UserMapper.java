package com.cardinalis.userservice.mapper;

import com.cardinalis.userservice.dao.HeaderResponse;
import com.cardinalis.userservice.dao.UserResponse;
import com.cardinalis.userservice.dao.request.AuthenticationRequest;
import com.cardinalis.userservice.dao.response.AuthUserResponse;
import com.cardinalis.userservice.dao.response.AuthenticationResponse;
import com.cardinalis.userservice.dao.response.FollowerUserResponse;
import com.cardinalis.userservice.dao.response.notification.NotificationResponse;
import com.cardinalis.userservice.exception.InputFieldException;
import com.cardinalis.userservice.model.Notification;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.UserRepository;
import com.cardinalis.userservice.repository.projection.user.FollowerUserProjection;
import com.cardinalis.userservice.repository.projection.user.UserProjection;
import com.cardinalis.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final BasicMapper basicMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public HeaderResponse<UserResponse> getFollowers(Long userId, Pageable pageable) {
        Page<UserProjection> users = userService.getFollowers(userId, pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public HeaderResponse<UserResponse> getFollowing(Long userId, Pageable pageable) {
        Page<UserProjection> users = userService.getFollowing(userId, pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }



    public Map<String, Object>  processFollow(Long userId) {
        Map<String, Object> notificationDetails = userService.processFollow(userId);
        return notificationDetails;
    }

    //    User Authentication
    AuthenticationResponse getAuthenticationResponse(Map<String, Object> credentials) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUser(modelMapper.map(credentials.get("user"), AuthUserResponse.class));
        response.setToken((String) credentials.get("token"));
        return response;
    }

    public AuthenticationResponse login(AuthenticationRequest request, BindingResult bindingResult) {
        processInputErrors(bindingResult);
        return getAuthenticationResponse(userService.login(request.getEmail(), request.getPassword()));
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


    private void processInputErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
    }

}