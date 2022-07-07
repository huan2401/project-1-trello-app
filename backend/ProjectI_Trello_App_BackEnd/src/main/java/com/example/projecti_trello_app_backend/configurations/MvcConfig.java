package com.example.projecti_trello_app_backend.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.example.projecti_trello_app_backend.configurations")
public class MvcConfig  implements WebMvcConfigurer {

    //cors config : cross- origin resource sharing
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedHeaders("*")
                        .allowedOrigins("http://**","https://**");
            }
        };
    }

}
