package org.cardinalis.tweetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class TweetServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(TweetServiceApplication.class, args);
	}
//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
}
