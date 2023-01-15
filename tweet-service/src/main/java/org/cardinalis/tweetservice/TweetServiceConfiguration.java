package org.cardinalis.tweetservice;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TweetServiceConfiguration {
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
