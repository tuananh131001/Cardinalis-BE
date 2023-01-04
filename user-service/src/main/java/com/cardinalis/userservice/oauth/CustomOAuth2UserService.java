package com.cardinalis.userservice.oauth;

import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // TODO Auto-generated method stub
        OAuth2User user =  super.loadUser(userRequest);
        try {
            return processLogin(userRequest, user);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }
    private OAuth2User processLogin(OAuth2UserRequest userRequest, OAuth2User oidcUser) {
        CustomOAuth2User googleUserInfo = new CustomOAuth2User((OAuth2User) oidcUser.getAttributes());

        // see what other data from userRequest or oidcUser you need
        LOG.info("Fetching timeline with username = " + googleUserInfo.getName());
        Optional<UserEntity> userOptional = userRepository.findByUsername(googleUserInfo.getName());
        if (userOptional.isEmpty()) {
            UserEntity user = new UserEntity();
            user.setUsername(googleUserInfo.getName());
            // set other needed data

            userRepository.save(user);
        }

        return oidcUser;
    }
}

