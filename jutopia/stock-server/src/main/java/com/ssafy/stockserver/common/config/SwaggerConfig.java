package com.ssafy.stockserver.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Stock Server",
                description = "Stock Server api명세",
                version = "v3"))
@RequiredArgsConstructor
@Configuration
// 스웨거 페이지 만들기
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/stock-server/api/**"};

        return GroupedOpenApi.builder()
                .group("stock server API v3")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().addServersItem(new Server().url("/"))
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new io.swagger.v3.oas.models.info.Info().title("springdoc API").version("V1")
                        .license(new License().name("Apache 2.0").url("<http://springdoc.org>")));
    }
}