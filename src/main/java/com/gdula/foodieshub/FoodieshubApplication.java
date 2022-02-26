package com.gdula.foodieshub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class }) //security disabled
public class FoodieshubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodieshubApplication.class, args);
	}

}
