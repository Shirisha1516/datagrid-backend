package com.tml.AIP_POSITION_JDG_TRANS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@SpringBootApplication
@EnableScheduling
public class AipCrmPositionJdgApplication {

	public static void main(String[] args) {
		SpringApplication.run(AipCrmPositionJdgApplication.class, args);
	}

}
