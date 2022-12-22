package org.cardinalis.tweetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient // It acts as a eureka client
@SpringBootApplication
public class TweetServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(TweetServiceApplication.class, args);
	}

}
