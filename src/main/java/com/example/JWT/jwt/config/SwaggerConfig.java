package com.example.JWT.jwt.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)

@Configuration
public class SwaggerConfig {

    public static  final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityContext> securityContexts(){
        return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }


    private List<SecurityReference> securityReferences(){
        AuthorizationScope scope = new AuthorizationScope("global", "Access everything");
        return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[]{scope}));
    }
    public Docket api(){

        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getInfo(){
        return new ApiInfo("Blogging Application : Backend Course",
                "This is swagger configuration for JWT",
                "1.0",
                "Terms of Service",
                new Contact("JWT example", "https://xyz.com",
                        "xyz@email.com"),
                "License of APIs",
                "API license URL",
                Collections.emptyList()
                );
    }
}
