package com.bhreneer.springdatacrudexample.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "com.bhreneer.springdatacrudexample")
@EnableJpaRepositories(basePackages = "com.bhreneer.springdatacrudexample.repository")
@EntityScan(basePackages = "com.bhreneer.springdatacrudexample.model")
@AutoConfiguration
public class SpringDataCrudExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataCrudExampleApplication.class, args);
	}

}
