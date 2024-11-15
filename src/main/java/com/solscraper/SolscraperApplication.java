package com.solscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SolscraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolscraperApplication.class, args);
	}

}
