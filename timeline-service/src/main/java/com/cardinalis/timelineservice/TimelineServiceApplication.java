package com.cardinalis.timelineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class },scanBasePackages = {"com.cardinalis.userservice","org.cardinalis.tweetservice"})
public class TimelineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimelineServiceApplication.class, args);
	}

}
