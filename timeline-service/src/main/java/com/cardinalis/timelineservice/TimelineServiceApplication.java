package com.cardinalis.timelineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient // It acts as a eureka client
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.cardinalis.userservice","org.cardinalis.tweetservice"})
public class TimelineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimelineServiceApplication.class, args);
	}

}
