package com.gdula.foodieshub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class }) //security disabled
public class FoodieshubApplication {
	public static void main(String[] args) {
		SpringApplication.run(FoodieshubApplication.class, args);
	}

}
