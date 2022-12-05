package org.cardinalis.tweetcrd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TweetCrdApplication {

	public static void main(String[] args) {

		SpringApplication.run(TweetCrdApplication.class, args);
	}

}
