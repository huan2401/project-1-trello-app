package com.example.projecti_trello_app_backend.configurations.api_docs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.Value;
import org.springframework.context.annotation.Configuration;

/*
    Define a authentication for APIs using SecurityScheme of OpenAPI 3.0
* */

@Configuration
@SecurityScheme(
        name = "methodBearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPISecurityConfig {
}
