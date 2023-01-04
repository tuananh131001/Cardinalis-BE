package com.cardinalis.userservice.oauth;

import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserServiceImpl userServiceImpl;
    @Override
    public void onAuthenticationSuccess(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws javax.servlet.ServletException, java.io.IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String loginName = oAuth2User.getName();
        System.out.println(loginName);
        UserEntity userEntity = userServiceImpl.fetchByUsername(loginName);
        if (userEntity == null) {
            System.out.println("User not found. Creating new user.");
        } else {
            System.out.println("User found. Updating user.");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
