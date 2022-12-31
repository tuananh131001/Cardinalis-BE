package com.cardinalis.gateway;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {
    @Autowired
    private JwtAuthenticationFilter filter;
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route(p -> p
                        .path("/tweet/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://tweet-service"))
                .route(p -> p
                        .path("/timeline/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://timeline-service"))
                .build();
    }
}