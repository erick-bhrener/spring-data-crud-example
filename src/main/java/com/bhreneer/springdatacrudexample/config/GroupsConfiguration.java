/*
package com.bhreneer.springdatacrudexample.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroupsConfiguration {
    @Bean
    public GroupedOpenApi publicApi() {
        return new OpenApi()
                .group("com.bhreneer.springdatacrudexample")
                .pathsToMatch("*")
                .packagesToScan("com.bhreneer.springdatacrudexample")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components()).info(new Info().title("Spring MVC REST API")
                .contact(new Contact().name("Erick Bhrener")).version("1.0.0"));
    }
}
*/
