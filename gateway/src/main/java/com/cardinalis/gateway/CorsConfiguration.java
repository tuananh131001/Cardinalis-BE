package com.cardinalis.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfiguration extends
        org.springframework.web.cors.CorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET","OPTIONS", "POST","PUT", "DELETE"));
        corsConfig.addAllowedHeader("Content-Type");

        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }}