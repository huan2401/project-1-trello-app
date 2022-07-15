package com.example.projecti_trello_app_backend;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ProjectITrelloAppBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectITrelloAppBackEndApplication.class, args);
    }


}
