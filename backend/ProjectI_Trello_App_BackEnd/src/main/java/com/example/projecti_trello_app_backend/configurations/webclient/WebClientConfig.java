package com.example.projecti_trello_app_backend.configurations.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

//    @Bean (name = "appWebClient")
//    public WebClient webClient()
//    {
//        return WebClient.builder().defaultCookie("cookieKey","cookieValue")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//   }
}
