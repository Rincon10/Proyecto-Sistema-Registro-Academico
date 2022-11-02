package org.perficient.registrationsystem.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class SwaggerConfig Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 * Para acceder a swagger http://localhost:8080/your-app-root/swagger-ui/
 * Para  Generear un proyecto con springboot usar https://start.spring.io/
 */

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Registration System API",
                "Some custom description of API.",
                "1.0",
                "Terms of service",
                new Contact("Camilo Rincon", "someUrl", "camilorincon100@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
