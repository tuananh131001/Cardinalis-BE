package org.cardinalis.tweetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableCaching
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
