package com.uday.joke.config;

import com.uday.joke.filter.ResponseHeadersFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JokeAppConfig {

    @Bean
    public ResponseHeadersFilter responseHeadersFilter(){
        return new ResponseHeadersFilter();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}