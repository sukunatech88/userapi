package com.tuempresa.userapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User API - Registro de Usuarios")
                        .version("1.0.0")
                        .description("API REST para registro de usuarios con validación, persistencia y generación de token JWT"));
    }
}