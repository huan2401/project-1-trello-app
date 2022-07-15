package com.example.projecti_trello_app_backend.configurations.api_docs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/*
* Define OpenAPI 3.0 with SecurityScheme for JWT authentication
* */


@Configuration
@ComponentScan(basePackages = "com.example.projecti_trello_app_backend.configurations")
public class OpenAPIConfig {

    @Bean
    public OpenAPI appOpenAPI()
    {
        List<Server> serverList = new ArrayList<>();
        serverList.add(new Server().url("http://localhost:8081").description("Local Server"));
        serverList.add(new Server().url("https://project1testingserver.herokuapp.com").description("Remote Server"));
        return new OpenAPI().servers(serverList)
                .info(new Info().contact(new Contact()
                        .email("chiendao1808@gmail.com")
                        .name("Chien Dao - Hanoi University of Science and Technology"))
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                        .version("1.0.0"));
//                .addSecurityItem(new SecurityRequirement()
//                                    .addList("bearerAuth"))
//                                    .components(new Components()
//                                                .addSecuritySchemes("bearerAuth",
//                                                                    new SecurityScheme().name("bearerAuth")
//                                                                    .type(SecurityScheme.Type.HTTP)
//                                                                    .bearerFormat("JWT")
//                                                                    .scheme("bearer")));

    }
}
