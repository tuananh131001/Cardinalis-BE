package com.cardinalis.userservice.config;

import com.cardinalis.userservice.security.oauth.CustomOAuth2UserService;
//import com.cardinalis.userservice.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.cardinalis.userservice.security.oauth.OAuth2LoginSuccessHandler;
import com.cardinalis.userservice.repository.UserRepository;
import com.cardinalis.userservice.security.RestAuthenticationEntryPoint;
import com.cardinalis.userservice.service.AuthenticationService;
import com.cardinalis.userservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class    SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private CustomOAuth2UserService customUserService;
    @Autowired
//    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }
//    @Bean
//    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
//        return new HttpCookieOAuth2AuthorizationRequestRepository();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers("/user/**", "/oauth2/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
//                    .authorizationEndpoint()
//                        .baseUri("/oauth2/authorize")
////                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
//                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(customUserService)
                        .and()
                        .successHandler(oAuth2LoginSuccessHandler)

               ;
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login();


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }
}