package com.sist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableScheduling
public class ShabbyProjectApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShabbyProjectApiServerApplication.class, args);
		
	}

}
