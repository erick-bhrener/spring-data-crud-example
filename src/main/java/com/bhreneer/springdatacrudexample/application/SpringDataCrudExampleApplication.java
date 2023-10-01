package com.bhreneer.springdatacrudexample.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringDataCrudExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataCrudExampleApplication.class, args);
	}

}
