package com.ssafy.memberserver.common.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
//    @Bean
//    public OpenAPI openAPI(){
//        return new OpenAPI()
//                .components(new Components())
//                .info(new Info()
//                        .title("springdoc")
//                        .description("springodc")
//                        .version("1.0.0"));
//    }
//    @Bean
//    public ModelResolver modelResolver(ObjectMapper objectMapper){
//        return new ModelResolver(objectMapper);
//    }

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/member-server/api/**", "/api/**", "/member-server/api/**/**"};

        return GroupedOpenApi.builder()
                .group("member server API v1")
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

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper){
        return new ModelResolver(objectMapper);
    }
}
