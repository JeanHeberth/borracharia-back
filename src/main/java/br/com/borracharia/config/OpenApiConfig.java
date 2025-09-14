package br.com.borracharia.config;

import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api(){
        return new OpenAPI().info(new Info()
                .title("Borracharia API").version("v1")
                .description("API para gestão de serviços de borracharia"));
    }
}
