package com.cardinalis.userservice.oauth.user;

import com.cardinalis.userservice.exception.OAuth2AuthenticationProcessingException;
import com.cardinalis.userservice.model.AuthenticationProvider;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
//        if(registrationId.equalsIgnoreCase(AuthenticationProvider.google.toString())) {
////            return new GoogleOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(AuthenticationProvider.facebook.toString())) {
////            return new FacebookOAuth2UserInfo(attributes);
//        } else
        if (registrationId.equalsIgnoreCase(AuthenticationProvider.GITHUB.toString())) { // number 1
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}